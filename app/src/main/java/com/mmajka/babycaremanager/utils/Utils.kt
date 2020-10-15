package com.mmajka.babycaremanager.utils

import android.content.Context
import android.util.Log

class Utils(context: Context) {
    private var PRIVATE_MODE = 0
    private val PREFERENCE_NAME = "ID_VALUE"
    private val ID = "ID"

    val preference = context!!.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)

    fun onUserCheck(id: String): Boolean{
        val ID_TEMP = preference.getString("ID", "")
        if (ID_TEMP!!.isEmpty()){
            preference.edit().putString(ID, id).apply()
            Log.i("GDZIE TEN JEBANY LOG", "${preference.getString("ID", "")}")

            return false
        }else{
            Log.i("GDZIE TEN JEBANY LOG", "${preference.getString("ID", "")}")
            return true
        }
    }

    fun savePreferences(id: String){
        preference.edit().putString(ID, id).apply()
    }
}