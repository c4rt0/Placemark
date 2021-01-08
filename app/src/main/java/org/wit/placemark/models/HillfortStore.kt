package org.wit.placemark.models

interface HillfortStore {
  fun findById(id:Long) : HillfortModel?
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
}