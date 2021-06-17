package com.example.mydivarkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.concurrent.timerTask

class Splash_Activity : AppCompatActivity() {
    lateinit var timerTask: TimerTask
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_)

        var timer = Timer()

        timer.schedule(timerTask {

        var intent = Intent(applicationContext,MainActivity::class.java)
        finish()
        cancel()
        startActivity(intent)

        }, 3000, 1000)


        //schedule
    }
}