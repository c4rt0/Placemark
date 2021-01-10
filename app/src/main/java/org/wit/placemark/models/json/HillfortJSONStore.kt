package org.wit.placemark.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.exists
import org.wit.placemark.helpers.read
import org.wit.placemark.helpers.write
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.models.HillfortStore
import java.util.*

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {

  val context: Context
  var hillforts = mutableListOf<HillfortModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findById(id:Long) : HillfortModel? {
    val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
    return foundHillfort
  }

  override fun delete(hillfort: HillfortModel) {
    val foundHillfort: HillfortModel? = hillforts.find { it.id == hillfort.id }
    hillforts.remove(foundHillfort)
    serialize()
  }

  override fun findAll(): MutableList<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = generateRandomId()
    hillforts.add(hillfort)
    serialize()
  }


  override fun update(hillfort: HillfortModel) {
    hillforts.find {it.id == hillfort.id}.apply {
      this?.title = hillfort.title
      this?.description = hillfort.description
      this?.image = hillfort.image
      this?.lat = hillfort.lat
      this?.lng = hillfort.lng
      this?.zoom = hillfort.zoom
    }
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(hillforts, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    hillforts = Gson().fromJson(jsonString, listType)
  }
}