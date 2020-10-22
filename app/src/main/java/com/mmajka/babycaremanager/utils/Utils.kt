package com.mmajka.babycaremanager.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Utils(context: Context) {
    private var PRIVATE_MODE = 0
    private val PREFERENCE_NAME = "ID_VALUE"
    private val ID = "ID"

    val preference = context!!.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)

    //Sprawdza czy w sharedPrefs istnieje ID jeśli nie dodaje wpis
    fun onUserCheck(): Boolean{
        val ID_TEMP = preference.getString("ID", "")
        if (ID_TEMP!!.isEmpty()){
            Log.i("BASE ID: ", "${preference.getString("ID", "")}")
            return false
        }else{
            Log.i("BASE ID: ", "${preference.getString("ID", "")}")
            return true
        }
    }
    //Zapis  bazy do sharedPrefs
    fun savePreferences(id: String?){
        preference.edit().putString(ID, id).apply()
    }

    fun encrypt(a: String){
        var newA = a.reversed().toInt()*4
    }

    fun decrypt(a: String){
        var newA = a.reversed().toInt()/4
    }

}