package com.mmajka.babycaremanager.feeding

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.MainActivity
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.FeedingFragmentBinding
import kotlinx.android.synthetic.main.action_fragment.*
import kotlinx.android.synthetic.main.feeding_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class FeedingFragment : Fragment() {

    /*Jeśli wybrana zostanie lewa albo prawa pierś to ujawnia się timer. Jeśli formula to przycisk play zmienia się w check,
    timer się ukrywa a pokazuje edit text z dopiskiem ml. Jeśli wybór pada na posiłek to oba są ukryte i zostaje
    tylko check oraz comment ET
     */
    //TODO funkcja aktualizacji rekordu
    private lateinit var binding: FeedingFragmentBinding
    private lateinit var viewModel: FeedingViewModel
    private var isChoosed = false
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
        binding = DataBindingUtil.inflate(inflater, R.layout.feeding_fragment, container, false)
        binding.timerStart.isClickable = false
        return binding.root
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
            lockButtons()
            try {
                if (info.startsWith("Left")){
                    Log.i("Info: ","$info")
                    viewModel._isLeftSelected.value = true
                    viewModel._isRightSelected.value = false
                    viewModel._isFormulaSelected.value = false
                    viewModel._isMealSelected.value = false
                    viewModel._time.value = duration
                    binding.timerStart.visibility = View.GONE
                    binding.submit.visibility = View.VISIBLE
                }else if (info.startsWith(" Right")){
                    viewModel._isLeftSelected.value = false
                    viewModel._isRightSelected.value = true
                    viewModel._isFormulaSelected.value = false
                    viewModel._isMealSelected.value = false
                    viewModel._time.value = duration
                    binding.timerStart.visibility = View.GONE
                    binding.submit.visibility = View.VISIBLE
                }else if (info.startsWith("Formula")){
                    viewModel._isLeftSelected.value = false
                    viewModel._isRightSelected.value = false
                    viewModel._isFormulaSelected.value = true
                    viewModel._isMealSelected.value = false
                    viewModel._time.value = time
                }else if (info.startsWith("Meal")){
                    viewModel._isLeftSelected.value = false
                    viewModel._isRightSelected.value = false
                    viewModel._isFormulaSelected.value = false
                    viewModel._isMealSelected.value = true
                    viewModel._time.value = time
                }else{
                    viewModel._time.value = "00:00"
                }
            }catch (e: NullPointerException){
                Log.e("Bundle error: ","${e.message}")
            }
            binding.comment.setText(info)
            binding.acTitle.text = info
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedingViewModel::class.java)
        if (!isChoosed) binding.timerStart.isClickable = false
        viewModel._isLeftSelected.value = true
        viewModel._isRightSelected.value = false
        viewModel._isFormulaSelected.value = false
        viewModel._isMealSelected.value = false
        onButtonsClick()
        viewModel._time.value = "00:00"
        viewModel._time.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timer.text = newTime
        })

        viewModel._isLeftSelected.observe(viewLifecycleOwner, Observer { isSelected ->
            if (isSelected){
                scaleView(left)
               timer.text = viewModel.time.value
            }else{
                unscaleView(left)
            }
        })

        viewModel._isRightSelected.observe(viewLifecycleOwner, Observer { isSelected ->
            if (isSelected){
                scaleView(right)
                timer.text = viewModel.time.value
            }else{
                unscaleView(right)
            }
        })

        viewModel._isFormulaSelected.observe(viewLifecycleOwner, Observer { isSelected ->
            if (isSelected){
                scaleView(formula)
            }else{
                unscaleView(formula)
            }
        })

        viewModel._isMealSelected.observe(viewLifecycleOwner, Observer { isSelected ->
            if (isSelected){
                scaleView(meal)
            }else{
                unscaleView(meal)
            }
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
            val time = viewModel.getTime()
            viewModel.onTimerStop(binding.timer)
            viewModel.setActions("Feeding", "$title $info", duration, time)
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
            viewModel._isLeftSelected.value = true
            viewModel._isRightSelected.value = false
            viewModel._isFormulaSelected.value = false
            viewModel._isMealSelected.value = false
        }
        binding.right.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Right")
            binding.timerStart.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
            binding.edit.visibility = View.GONE
            viewModel._isLeftSelected.value = false
            viewModel._isRightSelected.value = true
            viewModel._isFormulaSelected.value = false
            viewModel._isMealSelected.value = false
        }
        binding.formula.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Formula")
            binding.timerStart.visibility = View.GONE
            binding.submit.visibility = View.VISIBLE
            binding.edit.visibility = View.VISIBLE
            binding.timer.text = viewModel.getTime()
            viewModel._isLeftSelected.value = false
            viewModel._isRightSelected.value = false
            viewModel._isFormulaSelected.value = true
            viewModel._isMealSelected.value = false
        }
        binding.meal.setOnClickListener {
            isChoosed = true
            binding.acTitle.setText("Meal")
            binding.timerStart.visibility = View.GONE
            binding.submit.visibility = View.VISIBLE
            binding.edit.visibility = View.VISIBLE
            binding.timer.text = viewModel.getTime()
            viewModel._isLeftSelected.value = false
            viewModel._isRightSelected.value = false
            viewModel._isFormulaSelected.value = false
            viewModel._isMealSelected.value = true
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

    private fun scaleView(view: View){
        view.animate().scaleX(1.15F).scaleY(1.15F)
    }

    private fun unscaleView(view: View){
        view.animate().scaleX(1F).scaleY(1F)
    }
}