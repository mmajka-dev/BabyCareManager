package com.mmajka.babycaremanager.feeding

import android.app.Application
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class FeedingViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val refActions = db.getReference(path!!).child("actions")
    var seconds = 0
    var minutes = 0
    var _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    var _timer = MutableLiveData<Boolean>()
    val timer: LiveData<Boolean>
        get() = _timer

    var _isLeftSelected = MutableLiveData<Boolean>()
    val isLeftSelected: LiveData<Boolean>
        get() = _isLeftSelected

    var _isRightSelected = MutableLiveData<Boolean>()
    val isRightSelected: LiveData<Boolean>
        get() = _isRightSelected

    var _isFormulaSelected = MutableLiveData<Boolean>()
    val isFormulaSelected: LiveData<Boolean>
        get() = _isFormulaSelected

    var _isMealSelected = MutableLiveData<Boolean>()
    val isMealSelected: LiveData<Boolean>
        get() = _isMealSelected

    fun setActions(title: String, info: String, duration: String, time: String){
        val id = Date().time.toString()
        val action = BasicActionEntity(id, title, getDate(), time, info, duration)
        refActions.child(id).setValue(action)
    }

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }

    fun getTime(): String{
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val time = if (minute < 10){
            "$hour:0$minute"
        }else{
            "$hour:$minute"
        }
        Log.i("TIME", "$time")
        return time
    }


    fun onTimerStart(){
        var s = ""
        var m = ""
        if (seconds<=58){
            seconds += 1
            Log.i("Timer sec:","$seconds")
        }else{
            minutes += 1
            seconds = 0
            Log.i("Timer sec:","$seconds")
            Log.i("Timer min:","$minutes")
        }

        if (minutes<10){
            m = "0$minutes"
        }else m ="$minutes"

        if (seconds<10){
            s ="0$seconds"
        }else s = "$seconds"

        val time = "$m:$s"
        _time.postValue(time)

    }

    fun onTimerStop(view: TextView){
        view.setText("00:00")
        _timer.value = true
        minutes = 0
        seconds = 0
    }

    fun onTimerPause(){
        _timer.value = true
    }

    private fun scaleIcon(view: Button){
        view.scaleX = 1.1F
        view.scaleY = 1.1F
    }

    private fun unscaleIcon(view: Button){
        view.scaleX = 1.0F
        view.scaleY = 1.0F
    }

    fun callTimePicker(context: Context, textView: TextView){
        val cal = Calendar.getInstance()
        var time = ""
        val builder = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            time = SimpleDateFormat("HH:mm").format(cal.time)
            textView.text = time
        }
        TimePickerDialog(context, builder, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

}