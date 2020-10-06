package com.mmajka.babycaremanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActionDao {

    @Query("SELECT * FROM `action`")
    fun getAll(): LiveData<List<BasicActionEntity>>

    @Insert
    fun insert(action: BasicActionEntity)

    @Delete
    fun delete(action: BasicActionEntity)

    @Query ("DELETE  FROM `action`")
    fun deleteAll()

}