package com.example.hexeng.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hexeng.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.complete_activity.*

class CompleteActivity: AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_activity)

//      Get the user's total score and update the textview.
        val score = intent.extras?.getInt("total-score")
        total_score.text = score.toString()

        setupButton()

//      Get an instance of FirebaseAuth retreive the current user's detail
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser

//      Increment user's total score in Firebase DB
        db.collection("Users").whereEqualTo("uid",user?.uid)
            .get().addOnSuccessListener { users ->
               for(user in users) {
                   var totalScore = user.data["score"].toString().toInt() + score!!

                   db.collection("Users").document(user.id).update(
                       "score",totalScore.toString()
                   )
               }
            }
    }

    private fun setupButton() {
//      Map the button to redirect to ProfileActivity when clicked.
        var intent = Intent(this, ProfileActivity::class.java)
        back_to_profile!!.setOnClickListener { startActivity(intent) }
    }
}
