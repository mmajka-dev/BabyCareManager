package com.mmajka.babycaremanager.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmajka.babycaremanager.data.FirebaseSource
import com.mmajka.babycaremanager.utils.Utils
import kotlin.random.Random

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val utilsInstance = Utils(application.applicationContext)
    val path = utilsInstance.preference.getString("ID","")
    val firebaseInstance = FirebaseSource(path!!)
    var isFirstTime = MutableLiveData<Boolean>()

    //Sprawdza czy aplikacja została urouchomiona pierwszy raz czy nie
    fun onFirstTime(): LiveData<Boolean> {
        isFirstTime.value = path == null
        return isFirstTime
    }

    //sprawdza czy w bazie istnieje już takie id, jeśli nie, generuje nowe
    fun generateID(): String{
        val list = firebaseInstance.getIDs()
        var id = Random.nextInt(0, 999999)
        do {
            id = Random.nextInt(0, 999999)
        }while(list.equals(id))
        Log.i("ID", "$id")
        return id.toString()
    }
}