package com.example.doggeforlife

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var session:SessionManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.hide()
        val mailView=findViewById<TextView>(R.id.mailView)
        val memberView = findViewById<TextView>(R.id.memberView)

        session = SessionManager(this)
        var email = session.fetchEmail()
        mailView.text="$email"

        var member = session.fetchMember()

        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        memberView.text="Member since: $displayThisDate"



        findViewById<Button>(R.id.history).setOnClickListener {
            val intent = Intent(this@ProfileActivity,LoginHistoryActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.changeMail).setOnClickListener {
            val intent = Intent(this@ProfileActivity,ChangeEmailActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.deleteBtn).setOnClickListener {
            val intent = Intent(this@ProfileActivity,AccountDeleteActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            val intent = Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}