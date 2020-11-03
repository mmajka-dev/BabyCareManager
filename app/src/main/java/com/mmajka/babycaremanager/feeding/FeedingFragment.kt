package com.mmajka.babycaremanager.feeding

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.FeedingFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FeedingFragment : Fragment() {

    /*Jeśli wybrana zostanie lewa albo prawa pierś to ujawnia się timer. Jeśli formula to przycisk play zmienia się w check,
    timer się ukrywa a pokazuje edit text z dopiskiem ml. Jeśli wybór pada na posiłek to oba są ukryte i zostaje
    tylko check oraz comment ET
     */

    private lateinit var binding: FeedingFragmentBinding
    private lateinit var viewModel: FeedingViewModel
    private var isChoosed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.feeding_fragment, container, false)
        binding.timerStart.isClickable = false
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedingViewModel::class.java)
        if (!isChoosed) binding.timerStart.isClickable = false
        onButtonsClick()

        viewModel._time.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timer.setText(newTime)
        })

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.timerStart.setOnClickListener {
            viewModel._timer.value = false
            binding.timerStart.visibility = View.GONE
            binding.timerPause.visibility = View.VISIBLE
            binding.timerStop.visibility = View.VISIBLE
            lockButtons()
            onTimerStart(context!!)
        }

        binding.timerPause.setOnClickListener {
            viewModel.onTimerPause()
            binding.timerPause.visibility = View.GONE
            binding.timerStart.visibility = View.VISIBLE
        }

        binding.timerStop.setOnClickListener {
            val title = binding.acTitle.text.toString()
            val info = binding.comment.text.toString()
            val duration = binding.timer.text.toString()
            val time = binding.timer.text.toString()
            viewModel.onTimerStop(binding.timer)
            viewModel.setActions("Feeding", "$title $info", duration, time )
            requireActivity().onBackPressed()
            binding.timerStart.visibility = View.VISIBLE
            binding.timerPause.visibility = View.GONE
            binding.timerStop.visibility = View.GONE
            Log.i("Timer Stop", "${viewModel.minutes}:${viewModel.seconds}")
            unlockButtons()
        }

        binding.submit.setOnClickListener {
            val title = binding.acTitle.text.toString()
            val info = binding.comment.text.toString()
            val time = binding.timer.text.toString()
            viewModel.onTimerStop(binding.timer)
            viewModel.setActions("Feeding", "$title $info", "", time )
            requireActivity().onBackPressed()
        }

        binding.edit.setOnClickListener {
            viewModel.callTimePicker(context!!, binding.timer)
        }
    }

    private fun onTimerStart(context: Context){
        GlobalScope.launch {
            delay(1000)
            while (viewModel.timer.value != true){
                viewModel.onTimerStart()
                delay(1000)
            }
        }
    }

    private fun onButtonsClick(){
        binding.left.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Left")
            binding.timerStart.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
            binding.edit.visibility = View.GONE
        }
        binding.right.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Right")
            binding.timerStart.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
            binding.edit.visibility = View.GONE
        }
        binding.formula.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Formula")
            binding.timerStart.visibility = View.GONE
            binding.submit.visibility = View.VISIBLE
            binding.edit.visibility = View.VISIBLE
            binding.timer.text = viewModel.getTime()
        }
        binding.meal.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Meal")
            binding.timerStart.visibility = View.GONE
            binding.submit.visibility = View.VISIBLE
            binding.edit.visibility = View.VISIBLE
            binding.timer.text = viewModel.getTime()
        }
    }

    private fun lockButtons(){
        binding.left.isClickable = false
        binding.right.isClickable = false
        binding.formula.isClickable = false
        binding.meal.isClickable = false
    }

    private fun unlockButtons(){
        binding.left.isClickable = true
        binding.right.isClickable = true
        binding.formula.isClickable = true
        binding.meal.isClickable = true
    }
}