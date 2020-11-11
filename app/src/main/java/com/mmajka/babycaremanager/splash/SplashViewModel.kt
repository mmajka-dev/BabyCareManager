package com.mmajka.babycaremanager.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.utils.Utils
import java.lang.IndexOutOfBoundsException
import kotlin.random.Random

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val utilsInstance = Utils(application.applicationContext)
    val path = utilsInstance.preference.getString("ID","")
    val firebaseInstance = FirebaseDatabase.getInstance()
    val ref = firebaseInstance.getReference(path!!)
    val refActions = firebaseInstance.getReference(path!!).child("actions")
    val actions = ArrayList<String>()


    //generuje ID w przypadku pierwszego uruchomienia aplikacji
    //Jeśli takie id istnieje w bazie generuje nowe aż będzie unikalne
    fun generateID(): String{
        val list = getIDs()
        var id = 0
        do {
            id = Random.nextInt(100000000, 999999999)
        }while(list.equals(id))
        return id.toString()
    }

    fun getIDs(): ArrayList<String>{
        var childrens = ArrayList<String>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "${error.code}: ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()){
                    snapshot.children.forEach {
                        childrens.add(snapshot.children.toString())
                    }
                }
            }
        })
        return childrens
    }

    fun loadDatabase(){
        refActions.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()){
                    for (i in snapshot.children){
                        val item = i.getValue(BasicActionEntity::class.java)
                        actions.add(item!!.id)
                    }
                    actions.reverse()
                    var index = actions.size-1
                    Log.i("Actions: ", "${actions.size}")
                    Log.i("Index: ", "${index}")
                    if (index > 100)
                        try {
                            while (index != 100){
                                Log.i("Delete: ", "Item ${actions.get(index)} was deleted.")
                                refActions.child(actions.get(index)).removeValue()
                                index -= 1
                            }
                        }catch (e: IndexOutOfBoundsException){
                            Log.i("Delete exception: ", "${e.message}")
                        }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error: ","${error.code}: ${error.message}/${error.details}")
            }
        })
    }

    fun trimDatabase(){

    }

    fun putID(id: String){
        utilsInstance.savePreferences(id)
    }
}