package com.mmajka.babycaremanager.settings

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.data.child
import com.mmajka.babycaremanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val ref = db.getReference(path!!).child("child")
    val refActions = db.getReference(path!!).child("actions")

    var _child = MutableLiveData<child>()
    val child: LiveData<child>
        get() = _child

    var _hasChanged = MutableLiveData<Boolean>()
    val hasChanged: LiveData<Boolean>
        get() = _hasChanged


    fun onDataChanged(n2: EditText, b2: TextView, bt: ImageView){
        n2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                bt.visibility = View.VISIBLE
            }
        })

        b2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                bt.visibility = View.VISIBLE
            }
        })
    }

    fun putChildInfo(n: String, b: String){
        val child = child(n,b)
        ref.setValue(child)
    }

    fun getChild(name: TextView, age : TextView){
        ref.orderByChild("index").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                name.setText(snapshot.child("name").value.toString())
                age.setText(snapshot.child("age").value.toString())
            }
        })
    }

    fun getPhotoPath(): Uri {
        val photo = prefInstance.getPhotoPath()
        return photo
    }

    fun clearList(){
        refActions.removeValue()
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