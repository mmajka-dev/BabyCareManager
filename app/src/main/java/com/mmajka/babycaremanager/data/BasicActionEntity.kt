package com.mmajka.babycaremanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "action")
class BasicActionEntity (@PrimaryKey(autoGenerate = true)val id: Int,
                         @ColumnInfo(name = "title") val title: String ="",
                         @ColumnInfo(name = "title") val date: String = "",
                         @ColumnInfo(name = "title") val time: String = "",
                         @ColumnInfo(name = "title") val info: String = "",
                         @ColumnInfo(name = "title") val duration: String = "")