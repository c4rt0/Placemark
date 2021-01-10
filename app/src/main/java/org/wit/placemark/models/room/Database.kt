package org.wit.placemark.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.placemark.models.HillfortModel

@Database(entities = arrayOf(HillfortModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun hillfortDao(): HillfortDao
}