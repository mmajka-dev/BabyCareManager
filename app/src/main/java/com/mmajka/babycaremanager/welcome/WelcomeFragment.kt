package com.mmajka.babycaremanager.welcome

import android.app.ActionBar
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.WelcomeFragmentBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.utils.Utils
import kotlinx.android.synthetic.main.welcome_fragment.*

class WelcomeFragment : Fragment() {


    private lateinit var viewModel: WelcomeViewModel
    private lateinit var binding: WelcomeFragmentBinding
    private var visible = false
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val utils = Utils(context!!)
        try {
            val uri = data!!.data
            utils.setPhotoPath(uri!!)
            photo.setImageURI(uri)
        }catch (e: NullPointerException){
            Snackbar.make(requireView(), "No image choosed", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false)
        checkPermissions()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        binding.done.setOnClickListener {
            setChildInfo()
            setConfigured()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).disallowAddToBackStack().commit()
        }

        binding.codeCard.setOnClickListener {
            when(visible){
                true -> {
                    hideCodeCard()
                }
                false ->{
                    showCodeCard()
                }
            }
        }

        binding.birthday.setOnClickListener {
            getDate()
        }
        binding.okCode.setOnClickListener {
            if (!binding.code.text.isEmpty()) {
                putID()
                setConfigured()
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    HomeFragment()
                ).disallowAddToBackStack().commit()
            }else{
                Snackbar.make(requireView(), getString(R.string.code_msg), Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.photo.setOnClickListener {
            selectImageInAlbum()
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

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    private fun checkPermissions(){
        val storageRead = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val storageWrite = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded = arrayListOf<String>()
        if(storageRead == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }else if(storageWrite == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }
    private fun showCodeCard(){
        visible = true
        binding.code.visibility = View.VISIBLE
        binding.okCode.visibility = View.VISIBLE
        binding.bgView.visibility = View.VISIBLE
        binding.photo.isEnabled = false
        binding.babyName.isEnabled = false
        binding.birthday.isEnabled = false
        binding.done.isEnabled = false
    }

    private fun hideCodeCard(){
        visible = false
        binding.code.visibility = View.GONE
        binding.okCode.visibility = View.GONE
        binding.bgView.visibility = View.GONE
        binding.photo.isEnabled = true
        binding.babyName.isEnabled = true
        binding.birthday.isEnabled = true
        binding.done.isEnabled = true
    }
}