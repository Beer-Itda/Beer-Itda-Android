package com.ddd4.synesthesia.beer.presentation.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.base.BaseActivity
import com.ddd4.synesthesia.beer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        fun start(context: Context) {
            context.run {
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}