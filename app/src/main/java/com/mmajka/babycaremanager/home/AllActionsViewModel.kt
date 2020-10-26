package com.mmajka.babycaremanager.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils

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
                Log.i("Error", "${error.message}")
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
}