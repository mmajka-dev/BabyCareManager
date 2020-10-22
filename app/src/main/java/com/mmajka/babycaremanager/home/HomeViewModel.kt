package com.mmajka.babycaremanager.home

import android.app.Application
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.data.child
import com.mmajka.babycaremanager.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(application: Application) : AndroidViewModel(application){
    val db = FirebaseDatabase.getInstance()
    val prefInstance = Utils(application.applicationContext)
    val path = prefInstance.preference.getString("ID", "")
    val ref = db.getReference(path!!).child("child")
    val refActions = db.getReference(path!!).child("actions")

    var _child = MutableLiveData<child>()
    val child: LiveData<child>
        get() = _child

    var _actions = MutableLiveData<ArrayList<BasicActionEntity>>()
    val actions: LiveData<ArrayList<BasicActionEntity>>
        get() = _actions



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

    fun getCare(): LiveData<ArrayList<BasicActionEntity>> {
        val items = ArrayList<BasicActionEntity>()
        db.setPersistenceEnabled(true)
        refActions.keepSynced(true)
        refActions.limitToLast(100).addValueEventListener(object : ValueEventListener {
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

    fun setActions(date: String, time: String, type: String, text: String, duration: String){
        val action = BasicActionEntity(date, time, type, text, duration)
        refActions.child(Date().time.toString()).setValue(action)

    }

    fun clearList(){
        refActions.removeValue()
        _actions.value!!.clear()
    }
}