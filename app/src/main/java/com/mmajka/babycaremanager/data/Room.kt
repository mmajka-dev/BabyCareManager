package com.mmajka.babycaremanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room

@Database(entities = arrayOf(BasicActionEntity::class), version = 1, exportSchema = false)
abstract class RoomDatabase: androidx.room.RoomDatabase() {

    abstract fun actionDao(): ActionDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}