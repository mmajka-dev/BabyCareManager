package com.mmajka.babycaremanager.diaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.MainActivity
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.DiaperFragmentBinding
import kotlinx.android.synthetic.main.diaper_fragment.*

class DiaperFragment : Fragment() {


    private lateinit var viewModel: DiaperViewModel
    private lateinit var binding: DiaperFragmentBinding

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var info: String
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var duration: String

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
        viewModel._isPeeSelected.value = false
        viewModel._isPooSelected.value = true
        binding.time.text = viewModel.getTime()
        var addInfo = ""

        viewModel._isPeeSelected.observe(viewLifecycleOwner, Observer {isSelected ->
            if (isSelected){
                scaleView(pee)
                addInfo = "Pee"
            }else{
                unscaleView(pee)
            }
        })

        viewModel._isPooSelected.observe(viewLifecycleOwner, Observer {isSelected ->
            if (isSelected){
                scaleView(poo)
                addInfo = "Poo"
            }else{
                unscaleView(poo)
                comment.setText("")
            }
        })

        //Do poprawy!!
        binding.poo.setOnClickListener {
            viewModel._isPooSelected.value = !viewModel._isPooSelected.value!!
            viewModel._isPeeSelected.value = !viewModel._isPeeSelected.value!!
        }

        binding.pee.setOnClickListener {
            viewModel._isPeeSelected.value = !viewModel._isPeeSelected.value!!
            viewModel._isPooSelected.value = !viewModel._isPooSelected.value!!
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

    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle != null){
            id = bundle.getString("id")!!
            title = bundle.getString("title")!!
            info = bundle.getString("info")!!
            date = bundle.getString("date")!!
            time = bundle.getString("time")!!
            duration = bundle.getString("duration")!!

            binding.time.text = time
            binding.comment.setText(info)
            if (info.equals("Pee")){
                viewModel._isPeeSelected.value = true
                viewModel._isPooSelected.value = false
                scaleView(pee)
            }else{
                viewModel._isPooSelected.value = true
                viewModel._isPeeSelected.value = false
                scaleView(poo)
            }

        }
    }

    private fun scaleView(view: View){
        view.animate().scaleX(1.1F).scaleY(1.1F)
    }

    private fun unscaleView(view: View){
        view.animate().scaleX(1F).scaleY(1F)
    }
}