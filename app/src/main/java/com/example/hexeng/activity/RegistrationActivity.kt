package com.example.hexeng.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.hexeng.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.create_account_activity.*


class RegistrationActivity : AppCompatActivity() {

    private var db = Firebase.firestore
    private var mAuth: FirebaseAuth? = null
    private val TAG = "CreateAccountActivity"

    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account_activity)

        initialise()
    }

    private fun initialise() {
//      Create an instance of FirebaseAuth
//      Set mapping to invoke createNewAccount() when clicked
        mAuth = FirebaseAuth.getInstance()
        btn_register!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount(){
//      Get inputs from edit text
        firstName = et_first_name?.text.toString()
        lastName = et_last_name?.text.toString()
        email = et_email?.text.toString()
        password = et_password?.text.toString()

//      Check for empty input
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//          Authentication process
            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid

//                      Send email verification
                        verifyEmail();

//                      Initialize user object
                        val user = hashMapOf(
                            "uid" to userId,
                            "firstname" to firstName,
                            "lastname" to lastName,
                            "email" to email,
                            "score" to 0
                        )

//                      Insert user object to FirebaseDB
                        db.collection("Users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

//                      Render new interface
                        updateUserInfoAndUI()
                    } else {

                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
//      Set mapping to start LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun verifyEmail() {
//      Send email verification to the input email account
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
