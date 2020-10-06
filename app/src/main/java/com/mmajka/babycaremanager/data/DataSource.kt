package com.mmajka.babycaremanager.data

import android.app.Application
import com.mmajka.babycaremanager.utils.Utils

class DataSource(): Application() {

    private lateinit var repository: Repository
    private val preferences = Utils(applicationContext).preference
    private val path = preferences.getString("ID", "")

    init {
        val actionDao = RoomDatabase.getDatabase(applicationContext).actionDao()
        val repository = Repository(actionDao, path!!)
    }

    fun onDataCheck(){
        //TODO last version of data check
    }

}