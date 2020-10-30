package com.mmajka.babycaremanager.splash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.SplashFragmentBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.utils.Utils
import com.mmajka.babycaremanager.welcome.WelcomeFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var  bind: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        return bind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        val check = onUserCheck()
        Log.i("CHECK2", "$check")
            if (check){
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).disallowAddToBackStack().commit()
            }else{
                newID()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WelcomeFragment()).disallowAddToBackStack().commit()
            }
    }

    private fun onUserCheck(): Boolean{
        val utilsInstance = Utils(context!!)
        val check = utilsInstance.onConfigCheck()
        Log.i("CHECK", "$check")
        return check
    }

    private fun newID(){
        val id = viewModel.generateID()
        viewModel.putID(id)
        Log.i("ID", "$id")
        Toast.makeText(context, "$id", Toast.LENGTH_SHORT).show()
    }
}