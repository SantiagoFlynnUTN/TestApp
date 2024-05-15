package com.flynn.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flynn.data.local.room.entity.AboutEntity

@Database(entities = [AboutEntity::class], version = CompassDb.VERSION, exportSchema = false)
abstract class CompassDb : RoomDatabase() {
    abstract fun aboutDao(): AboutDao

    companion object {
        internal const val VERSION = 1
        private const val NAME = "compass_db"

        fun create(applicationContext: Context): CompassDb {
            return Room.databaseBuilder(applicationContext, CompassDb::class.java, NAME).build()
        }
    }
}