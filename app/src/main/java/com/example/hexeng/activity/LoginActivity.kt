package com.example.hexeng.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hexeng.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var email: String? = null
    private var password: String? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        initialise()
    }

    private fun initialise() {
//      Create an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

//      Set mapping to the register
        btn_register_account!!
            .setOnClickListener { startActivity(
                Intent(this,
                RegistrationActivity::class.java)
            ) }
//      Set mapping to the login button
        btn_login!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
//      Get the email, password from edit text
        email = et_email?.text.toString()
        password = et_password?.text.toString()

//      Check if the inputs are empty or not
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

//          Perform authentication using Firebase Auth
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
//                      Render the new view after successfully logged in
                        updateUI()
                    } else {
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
//      Start UserProfile activity after invoke
        val intent = Intent(this, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
