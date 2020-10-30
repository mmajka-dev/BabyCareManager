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
    private val IS_CONFIGURED = "isConfigured"

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

    fun onConfigCheck(): Boolean{
        val config = preference.getString(IS_CONFIGURED, "")
        if (config!!.isEmpty()){
            Log.i("CHECK: ", "${preference.getString(IS_CONFIGURED, "")}")
            return false
        }else{
            Log.i("CHECK: ", "${preference.getString(IS_CONFIGURED, "")}")
            return true
        }
    }

    //Zapis  bazy do sharedPrefs
    fun savePreferences(id: String?){
        preference.edit().putString(ID, id).apply()
    }

    fun isConfigured(isConfigured: String){
        preference.edit().putString(IS_CONFIGURED, isConfigured).apply()
    }

    fun encrypt(a: String){
        var newA = a.reversed().toInt()*4
    }

    fun decrypt(a: String){
        var newA = a.reversed().toInt()/4
    }

}