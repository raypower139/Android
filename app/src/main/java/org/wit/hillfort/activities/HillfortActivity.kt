package org.wit.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImage
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.Location
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.HillfortMemStore
import org.wit.hillfort.models.HillfortStore
import android.widget.CheckBox
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class HillfortActivity : AppCompatActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var i = 0

    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_nav)
        toolbarAdd.title = title

        setSupportActionBar(findViewById(R.id.toolbarAdd))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        info("Hillfort Activity started..")

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        app = application as MainApp
        var edit = false
        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            if (hillfort.visited) {
                visited_checkbox.isChecked = true
            }
                btnAdd.setText(R.string.save_hillfort)
                //if (hillfort.image.size != 0) {
                //chooseImage.setText(R.string.change_hillfort_image)
                //hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image[0]))
                //hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image[1]))
                // hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image[2]))
                //hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image[3]))
                //}
            }

            visited_checkbox.setOnClickListener(View.OnClickListener {
                if (visited_checkbox.isChecked) {
                    hillfort.visited = true
                } else {
                    hillfort.visited = false
                }
            })

            btnAdd.setOnClickListener() {
                hillfort.title = hillfortTitle.text.toString()
                hillfort.description = description.text.toString()
                hillfort.visited = hillfort.visited
                if (hillfort.title.isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    if (edit) {
                        app.hillforts.update(hillfort.copy())
                    } else {
                        app.hillforts.create(hillfort.copy())
                    }
                }
                info("add Button Pressed: $hillfortTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }

            chooseImage.setOnClickListener {
                info("Select image")
                showImagePicker(this, IMAGE_REQUEST)

            }
            hillfortImage.setOnClickListener {

                if (hillfort.image.size != 0) {
                    i += 1
                    if (i == hillfort.image.size) {
                        i = 0
                    }
                    hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image[i]))
                }
            }


            hillfortLocation.setOnClickListener {
                val location = Location(52.245696, -7.139102, 15f)
                if (hillfort.zoom != 0f) {
                    location.lat = hillfort.lat
                    location.lng = hillfort.lng
                    location.zoom = hillfort.zoom
                }
                startActivityForResult(
                    intentFor<MapActivity>().putExtra("location", location),
                    LOCATION_REQUEST
                )
            }
        }


        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_hillfort, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {


            when (item?.itemId) {
                R.id.item_cancel -> {
                    finish()
                }
                R.id.item_delete -> {
                    app.hillforts.delete(hillfort)
                    finish()
                }
                R.id.item_settings -> startActivity<SettingsActivity>()
                R.id.action_logout -> startActivity<LoginActivity>()
                R.id.action_close -> finishAffinity()
            }
            return super.onOptionsItemSelected(item)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                IMAGE_REQUEST -> {
                    if (data != null) {
                        hillfort.image.add(data.getData().toString())
                        hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                        Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                        chooseImage.setText(R.string.change_hillfort_image)
                    }

                }
                LOCATION_REQUEST -> {
                    if (data != null) {
                        val location = data.extras?.getParcelable<Location>("location")!!
                        hillfort.lat = location.lat
                        hillfort.lng = location.lng
                        hillfort.zoom = location.zoom
                    }
                }
            }

        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.action_add -> startActivity<HillfortActivity>()
            R.id.action_settings -> startActivity<SettingsActivity>()
            R.id.action_logout -> startActivity<LoginActivity>()
            R.id.action_close -> finishAffinity()
        }
        return true
    }

}



