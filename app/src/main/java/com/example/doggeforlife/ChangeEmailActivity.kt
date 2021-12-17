package com.example.doggeforlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmailActivity : AppCompatActivity() {

    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)
        supportActionBar?.hide()
        val emailchange=findViewById<TextInputLayout>(R.id.email1).editText?.text

        val confirmButton=findViewById<Button>(R.id.changeemailbtn)
        confirmButton.setOnClickListener{
            val loading=LoadingDialog(this)
            loading.startLoading()
            val handler= Handler()
            handler.postDelayed(object:Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            },3000)
            val user=EmailUpdate(emailchange.toString())
            session = SessionManager(this)
            CoroutineScope(Dispatchers.IO).launch {


                val sampleApplication=application as LoginApplication
                val service=sampleApplication.changeemailService
                service.ChangeEmail("Bearer ${session.fetchAuthToken()}",user).enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if(response.isSuccessful)
                        {

                            val intent = Intent(this@ChangeEmailActivity,ProfileActivity::class.java)
                            startActivity(intent)
                            //Toast.makeText(applicationContext,response.body()?.email,Toast.LENGTH_LONG).show()

                        }
                        else
                        {

                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        //  TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}