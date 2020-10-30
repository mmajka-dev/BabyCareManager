package com.mmajka.babycaremanager

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.MaterialToolbar
import com.mmajka.babycaremanager.databinding.ActivityMainBinding
import com.mmajka.babycaremanager.diaper.DiaperFragment
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.splash.SplashFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    companion object{
        var CURRENT_ACTIVITY = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SplashFragment()).commit()
    }
}