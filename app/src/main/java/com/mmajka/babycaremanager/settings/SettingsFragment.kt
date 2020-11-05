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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.SettingsFragmentBinding
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
        val n = binding.name.text.toString()
        val b = binding.birthday.text.toString()

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        viewModel.getChild(name, birthday)

        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                save.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                save.visibility = View.VISIBLE
            }
        })

        birthday.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                save.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                save.visibility = View.VISIBLE
            }
        })

        viewModel.onDataChanged(name, birthday, save)

        Glide.with(this).load(viewModel.getPhotoPath()).error(R.drawable.ic_boy).into(imageView4)

        binding.inviteTxt.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                InviteFragment()
            ).addToBackStack("").commit()
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
            Snackbar.make(binding.root, "Changes saved", Snackbar.LENGTH_SHORT).show()
        }

        binding.imageView4.setOnClickListener {
            selectImageInAlbum()
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }
}