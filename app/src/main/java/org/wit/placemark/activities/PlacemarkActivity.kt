package org.wit.placemark.activities

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.*
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  var placemark = PlacemarkModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

    var edit  = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbarAdd.setNavigationOnClickListener {
      onBackPressed()
    }

    info("Placemark Activity started..")

    app = application as MainApp


    if (intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
      //placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image[0]))
      placemark.image.forEachIndexed { index, image ->
        when(index){
          0->placemarkImage.setImageBitmap(readImageFromPath(this, image))
          1->placemarkImage2.setImageBitmap(readImageFromPath(this, image))
        }
      }
      if (placemark.image.size>0) {
        chooseImage.setText(R.string.change_placemark_image)
      }
      btnAdd.setText(R.string.save_placemark)
    }

    btnAdd.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()
      if (placemark.title.isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        if (edit) {
          app.placemarks.update(placemark.copy())
        } else {
          app.placemarks.create(placemark.copy())
        }
        info("add Button Pressed: $placemarkTitle")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST);
    }

    placemarkLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (placemark.zoom != 0f) {
        location.lat =  placemark.lat
        location.lng = placemark.lng
        location.zoom = placemark.zoom
      }
      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    if(edit)
      menuInflater.inflate(R.menu.menu_placemark_edit, menu)
    else
      menuInflater.inflate(R.menu.menu_placemark, menu)

    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
            if(data.clipData != null){

                val clipData:ClipData = data.clipData!!
                if(clipData.itemCount >2){
                    Toast.makeText(applicationContext,"Maximum 2 images are allowed!",Toast.LENGTH_LONG).show()
                }else{
                    placemark.image = mutableListOf()
                    placemarkImage2.setImageDrawable(null)
                    for(i in 0 until clipData.itemCount){
                        placemark.image.add(clipData.getItemAt(i).uri.toString())
                      when(i){
                        0->placemarkImage.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
                        1->placemarkImage2.setImageBitmap(readImageFromPath(this,clipData.getItemAt(i).uri.toString()))
                      }
                    }
                    chooseImage.setText(R.string.change_placemark_image)
                }
            }else if(data.getData()!=null) {
                placemark.image = mutableListOf()
                placemarkImage2.setImageDrawable(null)
                placemark.image.add(data.getData().toString())
                placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                chooseImage.setText(R.string.change_placemark_image)
            }
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          placemark.lat = location.lat
          placemark.lng = location.lng
          placemark.zoom = location.zoom
        }
      }
    }
  }
}

