package com.mmajka.babycaremanager.welcome

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
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

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

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
            setConfigured()
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
            setConfigured()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).disallowAddToBackStack().commit()
        }

        binding.photo.setOnClickListener {
            openGalleryForPickingImage(1)
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

    private fun setConfigured(){
        val isConfigured = "true"
        viewModel.onConfigureChange(isConfigured)
    }

    private fun getDate(){
        val t = binding.birthday
        viewModel.callDatePicker(context!!, t)
    }

    fun Fragment.openGalleryForPickingImage(code: Int) {
        var path = "nic"
        Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            startActivityForResult(Intent.createChooser(this, getString(R.string.seletc_image)), code)
        }
    }
}