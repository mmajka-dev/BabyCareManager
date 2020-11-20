package com.mmajka.babycaremanager.splash

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.SplashFragmentBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.utils.ConnectionChecker
import com.mmajka.babycaremanager.utils.Utils
import com.mmajka.babycaremanager.welcome.WelcomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import www.sanju.motiontoast.MotionToast
import kotlin.coroutines.CoroutineContext



class SplashFragment : Fragment(), CoroutineScope {

    private lateinit var viewModel: SplashViewModel
    private lateinit var  bind: SplashFragmentBinding
    private  var wifiManager = requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

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
        val connectionChecker = ConnectionChecker(context!!)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val dialog = MaterialAlertDialogBuilder(context!!)
            .setView(R.layout.alert_dialog_fragment)
            .setCancelable(false)
        connectionChecker.observe(viewLifecycleOwner, Observer { isActive ->
            if (isActive){
                launch {
                    loadDatabase().await()
                    delay(1000)

                    if (check){
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                        fragmentTransaction.replace(R.id.fragment_container, HomeFragment(), "h")
                        fragmentTransaction.disallowAddToBackStack()
                        fragmentTransaction.commit()
                    }else{
                        newID()
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                        fragmentTransaction.replace(R.id.fragment_container, WelcomeFragment(), "h")
                        fragmentTransaction.disallowAddToBackStack()
                        fragmentTransaction.commit()
                    }
                }
            }else{
                Snackbar.make(bind.root, "No internet connection", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(context!!, R.color.colorWhite))
                    .setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                    .show()

            }
        })

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

    private fun loadDatabase() = CoroutineScope(Dispatchers.IO).async{
        viewModel.loadDatabase()
    }

}