package com.mmajka.babycaremanager.feeding

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.feeding_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedingViewModel::class.java)

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
            onTimerStart(context!!)
        }

        binding.timerPause.setOnClickListener {
            viewModel.onTimerStop()
            binding.timerPause.visibility = View.GONE
            binding.timerStart.visibility = View.VISIBLE
        }

        binding.timerStop.setOnClickListener {
            viewModel.onTimerStop()
            binding.timerStart.visibility = View.VISIBLE
            binding.timerPause.visibility = View.GONE
            binding.timerStop.visibility = View.GONE
            Log.i("Timer Stop", "${viewModel.minutes}:${viewModel.seconds}")
        }
    }

    private fun onTimerStart(context: Context){
        GlobalScope.launch {
            while (viewModel.timer.value != true){
                delay(1000)
                viewModel.onTimerStart(context)
            }
        }

    }

    private fun onButtonsClick(){
        binding.left.setOnClickListener {
            //TODO
        }
        binding.right.setOnClickListener {
            //TODO
        }
        binding.formula.setOnClickListener {
            //TODO
        }
        binding.meal.setOnClickListener {
            //TODO
        }
    }

}