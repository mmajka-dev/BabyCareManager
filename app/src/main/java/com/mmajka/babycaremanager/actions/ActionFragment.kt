package com.mmajka.babycaremanager.actions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.mmajka.babycaremanager.MainActivity.Companion.CURRENT_ACTIVITY
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.ActionFragmentBinding
import kotlinx.android.synthetic.main.action_fragment.*
import java.lang.NullPointerException

class ActionFragment : Fragment() {

    companion object {
        fun newInstance() = ActionFragment()
    }

    private lateinit var viewModel: ActionViewModel
    private lateinit var binding: ActionFragmentBinding

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var info: String
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var duration: String
    private lateinit var subtype: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.action_fragment, container, false)
        setupView(binding.appBar, binding.title, binding.submit, binding.time, binding.edit)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle != null){
            id = bundle.getString("id")!!
            title = bundle.getString("title")!!
            try {
                CURRENT_ACTIVITY = title
                setupView(binding.appBar, binding.title, binding.submit, binding.time, binding.edit)
            }catch (e: NullPointerException){
                Log.e("Bundle error", "${e.message}")
            }
            info = bundle.getString("info")!!
            date = bundle.getString("date")!!
            time = bundle.getString("time")!!
            duration = bundle.getString("duration")!!
            subtype = bundle.getString("subtype")!!

            try {
                when(subtype){
                    "sleep" ->{
                        binding.appBar.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                        binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorPurple))
                        binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                        binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                        binding.title.setText(getString(R.string.title_sleep))
                    }
                    "bath" ->{
                        binding.appBar.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                        binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorBlue))
                        binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                        binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                        binding.title.setText(getString(R.string.title_bath))
                    }
                    "walk" ->{
                        binding.appBar.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                        binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
                        binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                        binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                        binding.title.setText(getString(R.string.title_walk))
                    }
                }
            }catch (e: NullPointerException){
                Log.e("Bundle error: ","${e.message}")
            }
            binding.time.text = time
            binding.comment.setText(info)
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActionViewModel::class.java)
        binding.time.text = getTime()

        binding.edit.setOnClickListener {
            viewModel.callTimePicker(context!!, binding.time)
        }

        binding.submit.setOnClickListener {
            val date = viewModel.getDate()
            val title = binding.title.text.toString()
            val time = binding.time.text.toString()
            val info = binding.comment.text.toString()
            viewModel.setActions(date, time, title, info, "", subtype)
            requireActivity().onBackPressed()
        }
    }

    private fun setupView(toolbar: AppBarLayout, title: TextView, submit: ImageButton, time: TextView, edit: ImageButton){
        when(CURRENT_ACTIVITY){
            "Sleep" ->{
                subtype = "sleep"
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorPurple))
                binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                binding.title.setText(getString(R.string.title_sleep))
            }
            "Bath" ->{
                subtype = "bath"
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorBlue))
                binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                binding.title.setText(getString(R.string.title_bath))
            }
            "Walk" ->{
                subtype = "walk"
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                binding.time.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
                binding.submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                binding.edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                binding.title.setText(getString(R.string.title_walk))
            }
        }
    }

    private fun getTime(): String{
        val time = viewModel.getTime()
        return time
    }

}