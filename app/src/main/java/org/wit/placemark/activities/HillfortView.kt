package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.models.HillfortModel
class HillfortView : AppCompatActivity(), AnkoLogger {

  lateinit var presenter: HillfortPresenter
  var hillfort = HillfortModel()
// -----------------------------------------------------------
//  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

    var edit  = false
// -----------------------------------------------------------

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    presenter = HillfortPresenter(this)

// -----------------------------------------------------------
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbarAdd.setNavigationOnClickListener {
      onBackPressed()
    }

    info("Hillfort Activity started..")

//    app = application as MainApp


    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
//      hillfort.image.forEachIndexed { index, image } //->
//        when(index){
//          0->hillfortImage.setImageBitmap(readImageFromPath(this, image))
//          1->hillfortImage2.setImageBitmap(readImageFromPath(this, image))
//          2->hillfortImage3.setImageBitmap(readImageFromPath(this, image))
//          3->hillfortImage4.setImageBitmap(readImageFromPath(this, image))
//        }
//      }
//      if (hillfort.image.size>0) {
//        chooseImage.setText(R.string.change_hillfort_image)
//      }
      btnAdd.setText(R.string.save_hillfort)
    }

// -----------------------------------------------------------

    btnAdd.setOnClickListener() {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
        presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString())
      }
    }

    chooseImage.setOnClickListener { presenter.doSelectImage() }

//      showImagePicker(this, IMAGE_REQUEST);
//    }

    hillfortLocation.setOnClickListener { presenter.doSetLocation() }
//      val location = Location(52.245696, -7.139102, 15f)
//      if (hillfort.zoom != 0f) {
//        location.lat =  hillfort.lat
//        location.lng = hillfort.lng
//        location.zoom = hillfort.zoom
//      }
//      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
//    }
  }

  fun showHillfort(hillfort: HillfortModel) {
    hillfortTitle.setText(hillfort.title)
    description.setText(hillfort.description)
    hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
    if (hillfort.image != null) {
      chooseImage.setText(R.string.change_hillfort_image)
    }
    btnAdd.setText(R.string.save_hillfort)
  }

//  override fun onCreateOptionsMenu(menu: Menu): Boolean {
//    if(edit)
//      menuInflater.inflate(R.menu.menu_hillfort_edit, menu)
//    else
//      menuInflater.inflate(R.menu.menu_hillfort, menu)
//
//    return super.onCreateOptionsMenu(menu)
//  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }


//  override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    when (item.itemId) {
//      R.id.item_cancel -> {
//        finish()
//      }
//      R.id.item_delete ->{
//        alert("You are deleting hillfort") {
//          title = "Warning"
//          positiveButton("Delete"){
//            app.hillforts.delete(hillfort)
//            Toast.makeText(applicationContext,"Selected hillfort is now deleted",Toast.LENGTH_LONG).show()
//            setResult(AppCompatActivity.RESULT_OK)
//            finish()
//          }
//          negativeButton("Cancel"){}
//        }.show()
//      }
//    }
//    return super.onOptionsItemSelected(item)
//  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_cancel -> {
        presenter.doCancel()
      }
    }
    return super.onOptionsItemSelected(item)
  }

//
//  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//    when (requestCode) {
//      IMAGE_REQUEST -> {
//        if (data != null) {
//            if(data.clipData != null){
//
//                val clipData:ClipData = data.clipData!!
//                if(clipData.itemCount >4){
//                    Toast.makeText(applicationContext,"Maximum 4 images are allowed!",Toast.LENGTH_LONG).show()
//                }else{
//                    hillfort.image = mutableListOf()
//                    hillfortImage2.setImageDrawable(null)
//                    hillfortImage3.setImageDrawable(null)
//                    hillfortImage4.setImageDrawable(null)
//                    for(i in 0 until clipData.itemCount){
//                        hillfort.image.add(clipData.getItemAt(i).uri.toString())
//                      when(i){
//                        0->hillfortImage.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
//                        1->hillfortImage2.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
//                        2->hillfortImage3.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
//                        3->hillfortImage4.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
//                      }
//                    }
//                    chooseImage.setText(R.string.change_hillfort_image)
//                }
//            }else if(data.getData()!=null) {
//                hillfort.image = mutableListOf()
//                hillfortImage2.setImageDrawable(null)
//                hillfortImage3.setImageDrawable(null)
//                hillfortImage4.setImageDrawable(null)
//
//                hillfort.image.add(data.getData().toString())
//                hillfortImage.setImageBitmap(readImage(this, resultCode, data))
//                chooseImage.setText(R.string.change_hillfort_image)
//            }
//        }
//      }
//      LOCATION_REQUEST -> {
//        if (data != null) {
//          val location = data.extras?.getParcelable<Location>("location")!!
//          hillfort.lat = location.lat
//          hillfort.lng = location.lng
//          hillfort.zoom = location.zoom
//        }
//      }
//    }
//  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }
}


