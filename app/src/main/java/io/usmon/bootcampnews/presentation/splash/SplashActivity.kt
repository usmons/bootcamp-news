package io.usmon.bootcampnews.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.usmon.bootcampnews.R
import io.usmon.bootcampnews.presentation.MainActivity

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Yes I know Handle() is deprecated in Java but it's the easiest way :))
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java).also {
                this.finish()
            })
        }, 2000)

    }
}