package org.wit.placemark.views.hillfortlist

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.views.BasePresenter
import org.wit.placemark.views.BaseView
import org.wit.placemark.views.VIEW

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadHillforts() {
        doAsync {
            val placemarks = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(placemarks)
            }
        }
    }

}