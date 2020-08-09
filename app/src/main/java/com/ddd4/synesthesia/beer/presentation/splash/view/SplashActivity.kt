package com.ddd4.synesthesia.beer.presentation.splash.view

import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.base.BaseActivity
import com.ddd4.synesthesia.beer.databinding.ActivitySplashBinding
import com.ddd4.synesthesia.beer.presentation.main.view.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.button.setOnClickListener {
            MainActivity.start(this)
        }
    }
}