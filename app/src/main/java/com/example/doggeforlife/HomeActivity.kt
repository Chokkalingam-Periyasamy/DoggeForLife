package com.example.doggeforlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawermenu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        setSupportActionBar(toolbar)

        val toggle= ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled=true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.d)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.nav_profile ->{
                    val intent = Intent(this@HomeActivity,ProfileActivity::class.java)
                    startActivity(intent)

                }
                R.id.nav_interest ->{
                    val intent = Intent(this@HomeActivity,InterestActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_otherusers ->{
                    val intent = Intent(this@HomeActivity,OtherusersActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        val string = intent.getStringExtra("string")
        val apiclient = application as LoginApplication
        session = SessionManager(this)
        var intent = Intent(this, LoginActivity::class.java)

        var token = session.fetchAuthToken()
        val items: MutableList<PetsData> = mutableListOf<PetsData>()
        if (session.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.petsService.GetPets("Bearer "+token)
                var i = 0
                withContext(Dispatchers.Main)
                {
                    if (result.isSuccessful) {
                        while (i < result.body()?.pets!!.size) {
                            items.add(result.body()?.pets!![i])
                            i += 1
                        }
                    } else {
                        startActivity(intent)
                    }
                    val recycle = findViewById<RecyclerView>(R.id.recycleView)
                    recycle.adapter = AdapterClass(items)
                    recycle.layoutManager = LinearLayoutManager(this@HomeActivity)
                }

            }
        } else
            startActivity(intent)

    }
}