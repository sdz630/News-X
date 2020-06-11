package com.sdz.newsapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.sdz.newsapp.main.MainActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AsyncTask.execute(Runnable {
            Thread.sleep(3000)
            startActivity(Intent(this, MainActivity::class.java))
        })
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}