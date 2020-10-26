package com.mmajka.babycaremanager.actions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.mmajka.babycaremanager.MainActivity.Companion.CURRENT_ACTIVITY
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.ActionFragmentBinding

class ActionFragment : Fragment() {

    companion object {
        fun newInstance() = ActionFragment()
    }

    private lateinit var viewModel: ActionViewModel
    private lateinit var binding: ActionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.action_fragment, container, false)
        Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
        setupView(binding.appBar, binding.title, binding.submit, binding.time, binding.edit)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
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
            val title = CURRENT_ACTIVITY
            val time = binding.time.text.toString()
            val info = binding.comment.text.toString()
            viewModel.setActions(date, time, title, info, "")
            requireActivity().onBackPressed()
        }
    }

    private fun setupView(toolbar: AppBarLayout, title: TextView, submit: ImageButton, time: TextView, edit: ImageButton){
        when(CURRENT_ACTIVITY){
            "Sleep" ->{
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                time.setTextColor(ContextCompat.getColor(context!!, R.color.colorPurple))
                submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorPurple))
                title.setText("Sleep")
            }
            "Bath" ->{
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                time.setTextColor(ContextCompat.getColor(context!!, R.color.colorBlue))
                submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorBlue))
                title.setText("Bath")
            }
            "Walk" ->{
                toolbar.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                time.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
                submit.background.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                edit.drawable.setTint(ContextCompat.getColor(context!!, R.color.colorGreen))
                title.setText("Walk")
            }
        }
    }

    private fun getTime(): String{
        val time = viewModel.getTime()
        return time
    }
}