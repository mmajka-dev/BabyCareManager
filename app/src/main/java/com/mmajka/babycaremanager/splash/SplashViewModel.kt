package com.mmajka.babycaremanager.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mmajka.babycaremanager.utils.Utils
import kotlin.random.Random

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val utilsInstance = Utils(application.applicationContext)
    val path = utilsInstance.preference.getString("ID","")
    val firebaseInstance = FirebaseDatabase.getInstance()
    val ref = firebaseInstance.getReference(path!!)
    var isFirstTime = MutableLiveData<Boolean>()

    //Sprawdza czy aplikacja została urouchomiona pierwszy raz czy nie
    fun onFirstTime(): LiveData<Boolean> {
        isFirstTime.value = path == null
        return isFirstTime
    }

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

    fun putID(id: String){
        utilsInstance.savePreferences(id)
    }
}