package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.HillfortStore
import org.wit.placemark.models.firebase.HillfortFireStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    //hillfort = HillfortJSONStore(applicationContext)
    //hillfort = HillfortStoreRoom(applicationContext)
    hillforts = HillfortFireStore(applicationContext)
    info("Hillfort started")
  }
}