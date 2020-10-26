package com.mmajka.babycaremanager.diaper

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mmajka.babycaremanager.MainActivity
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.DiaperFragmentBinding

class DiaperFragment : Fragment() {


    private lateinit var viewModel: DiaperViewModel
    private lateinit var binding: DiaperFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.diaper_fragment, container, false)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()

        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiaperViewModel::class.java)
        binding.time.text = viewModel.getTime()
        var addInfo = ""
        //Do poprawy!!
        binding.poo.setOnClickListener {
            scaleView(binding.poo)
            addInfo = "Poo"
            binding.poo.setOnClickListener {
                unscaleView(binding.poo)
            }
        }

        binding.pee.setOnClickListener {
            scaleView(binding.pee)
            addInfo = "Pee"
            binding.pee.setOnClickListener {
                unscaleView(binding.pee)
            }
        }

        binding.edit.setOnClickListener {
            viewModel.callTimePicker(context!!, binding.time)
        }

        binding.submit.setOnClickListener {
            val date = viewModel.getDate()
            val title = MainActivity.CURRENT_ACTIVITY
            val time = binding.time.text.toString()
            val info = "$addInfo ${binding.comment.text}"
            viewModel.setActions(date, time, title, info, "")
            requireActivity().onBackPressed()
        }
    }

    private fun scaleView(view: View){
        view.animate().scaleX(1.1F)
        view.animate().scaleY(1.1F)
    }

    private fun unscaleView(view: View){
        view.animate().scaleX(1F)
        view.animate().scaleY(1F)
    }
}