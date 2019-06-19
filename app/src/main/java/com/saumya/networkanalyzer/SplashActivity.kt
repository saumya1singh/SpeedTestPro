package com.saumya.networkanalyzer

import android.content.Intent

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val SlashLenght=2000L
    private val handler=Handler()
    private val runnable:Runnable= Runnable {
        if(!isFinishing){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable,SlashLenght)
    }

    override fun onDestroy() {

        if (handler!=null){
            handler.removeCallbacks { runnable }
        }
        super.onDestroy()
    }
}
