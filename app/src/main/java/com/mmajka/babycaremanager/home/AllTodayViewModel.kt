package com.mmajka.babycaremanager.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

class AllTodayViewModel(application: Application) : AndroidViewModel(application) {

    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val refActions = db.getReference(path!!).child("actions")

    var _today = MutableLiveData<ArrayList<BasicActionEntity>>()
    val actoday: LiveData<ArrayList<BasicActionEntity>>
        get() = _today

    fun getToday(): LiveData<ArrayList<BasicActionEntity>> {
        val items = ArrayList<BasicActionEntity>()
        refActions.keepSynced(true)
        refActions.orderByChild("date").equalTo(getDate()).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear()
                for(i in snapshot.children) {
                    val item = i.getValue(BasicActionEntity::class.java)
                    items.add(item!!)
                    _today.value = items
                }
            }
        })
        return _today
    }

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }

}