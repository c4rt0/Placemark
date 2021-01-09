package org.wit.placemark.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.views.BaseView

class HillfortView : BaseView(), AnkoLogger {

  lateinit var presenter: HillfortPresenter
  var hillfort = HillfortModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    init(toolbarAdd)

    presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter

    chooseImage.setOnClickListener {
      presenter.cachePlacemark(hillfortTitle.text.toString(), description.text.toString())
      presenter.doSelectImage()
    }

    hillfortLocation.setOnClickListener {
      presenter.cachePlacemark(hillfortTitle.text.toString(), description.text.toString())
      presenter.doSetLocation()
    }
  }

  override fun showHillfort(hillfort: HillfortModel) {
    hillfortTitle.setText(hillfort.title)
    description.setText(hillfort.description)
    hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
    if (hillfort.image != null) {
      chooseImage.setText(R.string.change_hillfort_image)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_save -> {
        if (hillfortTitle.text.toString().isEmpty()) {
          toast(R.string.enter_hillfort_title)
        } else {
          presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString())
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onBackPressed() {
    presenter.doCancel()
  }
}