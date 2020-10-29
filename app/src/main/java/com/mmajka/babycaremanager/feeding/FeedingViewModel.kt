package com.mmajka.babycaremanager.feeding

import android.app.Application
import android.content.Context
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils
import kotlinx.coroutines.*
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

    fun setActions(title: String, info: String, duration: String){
        val action = BasicActionEntity(title, getDate(), getTime(), info, duration)
        refActions.child(Date().time.toString()).setValue(action)
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

}