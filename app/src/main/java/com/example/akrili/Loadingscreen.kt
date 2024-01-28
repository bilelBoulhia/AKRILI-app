package com.example.akrili

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class loadingscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loadingscreen)
        supportActionBar?.hide()

       Handler().postDelayed({
                              val intent = Intent( this@loadingscreen, loginpage::class.java)
            startActivity(intent)
           finish()
        },3000)
    }
}

