package com.mmajka.babycaremanager.splash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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
        if (onUserCheck()){
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).disallowAddToBackStack().commit()
        }else{
            Snackbar.make(bind.root, "Sasin przejebał 70 mln", Snackbar.LENGTH_SHORT).show()
            //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, config).disallowAddToBackStack().commit()
        }

    }

    private fun onUserCheck(): Boolean{
        val id = viewModel.generateID()
        val prefInstance = Utils(context!!)

        return prefInstance.onUserCheck(id)
    }
}