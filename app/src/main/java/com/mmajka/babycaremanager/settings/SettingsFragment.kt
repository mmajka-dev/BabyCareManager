package com.mmajka.babycaremanager.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.actions.ActionFragment
import com.mmajka.babycaremanager.data.child
import com.mmajka.babycaremanager.databinding.SettingsFragmentBinding
import com.mmajka.babycaremanager.home.HomeFragment
import com.mmajka.babycaremanager.invite.InviteFragment
import com.mmajka.babycaremanager.utils.Utils
import kotlinx.android.synthetic.main.all_today_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.birthday
import kotlinx.android.synthetic.main.welcome_fragment.*

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val utils = Utils(context!!)
        try {
            val uri = data!!.data
            utils.setPhotoPath(uri!!)
            imageView4.setImageURI(uri)
        }catch (e: NullPointerException){
            Snackbar.make(requireView(), "No image choosed", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val container = R.id.fragment_container
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        viewModel.getChild(name, birthday)
        Glide.with(this).load(viewModel.getPhotoPath()).error(R.drawable.ic_boy).into(imageView4)

        binding.inviteTxt.setOnClickListener {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, InviteFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }

        binding.clearTxt.setOnClickListener {
            val dialog = AlertDialog.Builder(context).setTitle("Clear")
                .setMessage("Clear database?")
                .setPositiveButton("Clear"){dialog, which ->
                    viewModel.clearList()
                }
                .setNegativeButton("Cancel"){dialog, which ->
                    dialog.dismiss()
                }
            dialog.show()
        }

        binding.birthday.setOnClickListener {
            viewModel.callDatePicker(context!!, birthday)
        }

        binding.save.setOnClickListener {
            val n1 = binding.name.text.toString()
            val b1 = binding.birthday.text.toString()
            viewModel.putChildInfo(n1, b1)
            Snackbar.make(binding.root, "Changes saved", Snackbar.LENGTH_SHORT).setBackgroundTint(resources.getColor(R.color.colorWhite))
                .setTextColor(resources.getColor(R.color.colorPrimary)).show()
        }

        binding.imageView4.setOnClickListener {
            selectImageInAlbum()
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.codeTxt.setOnClickListener {
            showCodeCard()
        }
        binding.okCode.setOnClickListener {
            if (!binding.code.text.isEmpty()) {
                putID()
                setChildInfo()
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    HomeFragment()
                ).disallowAddToBackStack().commit()
            }else{
                Snackbar.make(requireView(), getString(R.string.code_msg), Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.cancel.setOnClickListener {
            hideCodeCard()
        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1)
    }

    private fun putID(){
        val id = binding.code.text.toString()
        viewModel.putID(id)
    }

    fun showCodeCard(){
        binding.bgView.visibility = View.VISIBLE
        binding.codeCard.visibility = View.VISIBLE
        binding.name.isEnabled = false
        binding.birthday.isEnabled = false
        binding.clearTxt.isEnabled = false
        binding.inviteTxt.isEnabled = false
    }

    fun hideCodeCard(){
        binding.bgView.visibility = View.GONE
        binding.codeCard.visibility = View.GONE
        binding.name.isEnabled = true
        binding.birthday.isEnabled = true
        binding.clearTxt.isEnabled = true
        binding.inviteTxt.isEnabled = true
    }

    private fun setChildInfo(){
        val name = binding.name.text.toString()
        val birthday = binding.birthday.text.toString()
        viewModel.putChildInfo(name, birthday)
    }
}