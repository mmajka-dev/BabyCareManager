package com.mmajka.babycaremanager.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.mmajka.babycaremanager.utils.Utils
import java.util.prefs.Preferences

class Repository(val actionDao: ActionDao, path: String) {

    val allActionsLocal: LiveData<List<BasicActionEntity>> = actionDao.getAll()
    val allActionsOnline: LiveData<ArrayList<BasicActionEntity>> = FirebaseSource(path).getActions()

    fun insertLocal(action: BasicActionEntity){
        actionDao.insert(action)
    }

    fun delete(action: BasicActionEntity){
        actionDao.delete(action)
    }

    fun deleteAll(){
        actionDao.deleteAll()
    }
}