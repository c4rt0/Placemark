package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*
import org.jetbrains.anko.intentFor
import org.wit.placemark.R
import org.wit.placemark.views.hillfortlist.HillfortListView
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left)
        imageView1.startAnimation(slideAnimation)

        Timer().schedule(3000){
            this@SplashScreenActivity.finish()
            startActivity(intentFor<HillfortListView>())
        }
    }
}
