package com.mmajka.babycaremanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mmajka.babycaremanager.databinding.ActivityMainBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.splash.SplashFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()
    }
}