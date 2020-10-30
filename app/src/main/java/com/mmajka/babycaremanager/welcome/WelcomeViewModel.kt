package com.mmajka.babycaremanager.welcome

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

import com.mmajka.babycaremanager.data.child
import com.mmajka.babycaremanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {

    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val ref = db.getReference(path!!).child("child")

    fun putChildInfo(n: String, b: String){
        val child = child(n,b)
        ref.setValue(child)
    }

    fun putID(id: String){
        prefInstance.savePreferences(id)
    }

    fun onConfigureChange(isConfigured: String){
        prefInstance.isConfigured(isConfigured)
    }

    fun callDatePicker(context: Context, t: TextView){
        val cal = Calendar.getInstance()
        var calDay = cal.get(Calendar.DAY_OF_MONTH)
        var calMonth = cal.get(Calendar.MONTH)
        var calYear = cal.get(Calendar.YEAR)
        var date = ""
        val builder = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(cal.time)
            Toast.makeText(context, "$date", Toast.LENGTH_SHORT).show()
            t.setText(date)
        }
        DatePickerDialog(context, builder, calYear, calMonth, calDay).show()

    }


}