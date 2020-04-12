package com.example.hexeng

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hexeng.activity.CategoryActivity

class ProfileActivity : AppCompatActivity() {

    private var btnStart: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        initialise()
    }

    private fun initialise() {

        btnStart = findViewById<View>(R.id.btn_start) as Button

        btnStart!!.setOnClickListener { startActivity(
                Intent(this@ProfileActivity,
                    CategoryActivity::class.java)
            ) }
    }
}