package com.mmajka.babycaremanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class BasicActionEntity (var title: String ="",
                         var date: String = "",
                         var time: String = "",
                         var info: String = "",
                         var duration: String = "")