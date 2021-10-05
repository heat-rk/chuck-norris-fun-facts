package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ru.heatalways.chucknorrisfunfacts.databinding.ActivitySplashBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingActivity
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity

class SplashActivity: BindingActivity<ActivitySplashBinding>(
    bindingInflater = ActivitySplashBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java));
            finish();
        }, SPLASH_SCREEN_DURATION);
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION = 2000L
    }
}