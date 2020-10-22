package com.mmajka.babycaremanager.welcome

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.ActivityMainBindingImpl
import com.mmajka.babycaremanager.databinding.WelcomeFragmentBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.utils.Utils
import kotlinx.android.synthetic.main.invite_fragment.view.*

class WelcomeFragment : Fragment() {

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        binding.done.setOnClickListener {
            setChildInfo()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).disallowAddToBackStack().commit()
        }

        binding.codeCard.setOnClickListener {
            binding.code.visibility = View.VISIBLE
            binding.okCode.visibility = View.VISIBLE
        }

        binding.birthday.setOnClickListener {
            getDate()
        }
        binding.okCode.setOnClickListener {
            putID()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).disallowAddToBackStack().commit()
        }
    }


    private fun setChildInfo(){
        val name = binding.babyName.text.toString()
        val birthday = binding.birthday.text.toString()
        viewModel.putChildInfo(name, birthday)
    }

    private fun putID(){
        val id = binding.code.text.toString()
        viewModel.putID(id)
    }

    private fun getDate(){
        val t = binding.birthday
        viewModel.callDatePicker(context!!, t)
    }

}