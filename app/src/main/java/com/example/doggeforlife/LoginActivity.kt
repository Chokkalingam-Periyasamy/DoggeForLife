package com.example.doggeforlife

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        val sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)

        sessionManager = SessionManager(this,)

        findViewById<TextView>(R.id.register).setOnClickListener {
            val loading=LoadingDialog(this)
            loading.startLoading()
            val handler= Handler()
            handler.postDelayed(object:Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            },3000)
            val newScreenIntent= Intent(this,RegisterActivity::class.java)

            startActivity(newScreenIntent)
        }



        sessionManager = SessionManager(this,)

        findViewById<Button>(R.id.login).setOnClickListener {

            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text

            val user = UserData(email.toString(), password.toString())
            val email1=sharedPreferences.getString("email",null)

//            val profileIntent=Intent(this@LoginActivity,ProfileActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication = application as LoginApplication
                val service = sampleApplication.loginService

                service.postData(user).enqueue(object : Callback<Login?> {
                    override fun onResponse(
                        call: Call<Login?>,
                        response: Response<Login?>
                    ) {
                        if (response.isSuccessful) {

                            val dishesintent = Intent(this@LoginActivity,HomeActivity::class.java)
                            sessionManager.saveAuthToken(response.body()?.token)
                            sessionManager.saveEmail(response.body()?.email)
                            sessionManager.saveMember(response.body()!!.memberSince)
//                            profileIntent.putExtra("string",response.body()?.email)
//                            intent.putExtra("string",response.body()?.token)

                            startActivity(dishesintent)

                        } else {
                        }
                    }

                    override fun onFailure(call: Call<Login?>, t: Throwable) {

                    }
                })
            }
        }

    }

}
