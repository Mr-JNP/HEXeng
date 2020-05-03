package com.example.hexeng.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hexeng.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_activity.*

class ProfileActivity : AppCompatActivity() {


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        initialise()
    }

    private fun initialise() {
//      Get the detail of the current user
        val user = FirebaseAuth.getInstance().currentUser

//      If the user exists, update the text view.
        if (user != null) {
            db.collection("Users").whereEqualTo("uid",user.uid).get().addOnSuccessListener {users ->
                for( user in users) {
                    nameTextView.text = user.data["firstname"].toString()
                    text_score.text = user.data["score"].toString()
                }
            }
        }


//      Set mapping to start CategoryActivity when clicked.
        btn_start!!.setOnClickListener { startActivity(
                Intent(this@ProfileActivity,
                    CategoryActivity::class.java)
            )
            finish()
        }
    }
}