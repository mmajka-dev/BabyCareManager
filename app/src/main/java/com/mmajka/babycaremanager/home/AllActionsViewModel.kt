package com.mmajka.babycaremanager.home

import android.app.Application
import android.util.Log
import android.widget.Toast
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

class AllActionsViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val refActions = db.getReference(path!!).child("actions")

    var _actions = MutableLiveData<ArrayList<BasicActionEntity>>()
    val actions: LiveData<ArrayList<BasicActionEntity>>
        get() = _actions

    fun getCare(): LiveData<ArrayList<BasicActionEntity>> {
        val items = ArrayList<BasicActionEntity>()
        refActions.keepSynced(true)
        refActions.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", "${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear()
                for(i in snapshot.children) {
                    val item = i.getValue(BasicActionEntity::class.java)
                    items.add(item!!)
                    _actions.value = items
                }
            }
        })
        return _actions
    }

    fun getDate(): String{
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)+1
        val year = cal.get(Calendar.YEAR)
        val date = "$year-$month-$day"

        return date
    }

    fun deleteAction(id: String, position: Int){
        refActions.child(id).removeValue()
        _actions.value!!.removeAt(position)
    }
}