package com.mmajka.babycaremanager.feeding

import android.content.Context
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class FeedingViewModel : ViewModel() {
    var seconds = 0
    var minutes = 0
    var _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    var _timer = MutableLiveData<Boolean>()
    val timer: LiveData<Boolean>
    get() = _timer

    fun onTimerStart(context: Context){
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

    fun onTimerStop(){
        _timer.value = true
        minutes = 0
        seconds = 0
    }
}