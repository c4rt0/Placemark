package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.HillfortStore
import org.wit.placemark.room.HillfortStoreRoom

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortStoreRoom(applicationContext)
    info("Hillfort started")
  }
}