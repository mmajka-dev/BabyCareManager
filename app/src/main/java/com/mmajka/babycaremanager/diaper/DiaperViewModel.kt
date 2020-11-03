package com.mmajka.babycaremanager.diaper

import android.app.Application
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class DiaperViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val refActions = db.getReference(path!!).child("actions")

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

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }

    fun setActions(date: String, time: String, title: String, info: String, duration: String){
        val action = BasicActionEntity(title, date, time, info, duration)
        refActions.child(Date().time.toString()).setValue(action)
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