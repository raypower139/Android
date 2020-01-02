package org.wit.hillfort.views.hillfort

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.R.drawable.fav_black
import org.wit.hillfort.R.drawable.fav_white
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.Location
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.hillfortFavouritesList.HillfortFavouriteListView
import org.wit.hillfort.views.hillfortList.HillfortListView
import java.text.SimpleDateFormat
import java.util.*


class HillfortView : BaseView(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()

    var i = 0
    var cal = Calendar.getInstance()
    var edit = false


    // LAYOUT FOR NAVIGATION DRAWER
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        init(toolbarAdd)
        //getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        info("Hillfort Activity started..")

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        //val navView = findViewById<NavigationView>(R.id.nav_view)
        //navView.setNavigationItemSelectedListener(this)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val ratingButton = findViewById<Button>(R.id.ratingButton)
        ratingButton?.setOnClickListener {
            val msg = ratingBar.rating.toString()
            Toast.makeText(
                this@HillfortView,
                "Rating is: " + msg, Toast.LENGTH_SHORT
            ).show()
        }

        //  SET IMAGE AND CALENDER PICKER TO INVISIBLE
        hillfortImage.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.INVISIBLE);
        setDate.setVisibility(View.INVISIBLE);

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        visited_checkbox.setOnClickListener(View.OnClickListener {
            if (visited_checkbox.isChecked) {
                hillfort.visited = true
                datePicker.setVisibility(View.VISIBLE); //Set to visible
                setDate.setVisibility(View.VISIBLE); //Set to visible
            } else {
                hillfort.visited = false //Set to invisible
                datePicker.setVisibility(View.INVISIBLE); //Set to invisible
                setDate.setVisibility(View.INVISIBLE); //Set to invisible
            }
        })

        fav_button.setOnClickListener(View.OnClickListener {
            if (hillfort.favourite == false) {
                fav_button.setBackgroundResource(fav_black)
                hillfort.favourite = true
            } else if (hillfort.favourite == true) {
                fav_button.setBackgroundResource(fav_white)
                hillfort.favourite = false
            }
        })


        shareButton.setOnClickListener{

            val details = "Hillfort Details" +
                    "\n\nTitle: ${presenter.hillfort.title}" +
                    "\nDescription: ${presenter.hillfort.description}" +
                    "\nRating: ${presenter.hillfort.rating}" +
                    "\nLatitude: ${presenter.hillfort.location.lat}" +
                    "\nLatitude: ${presenter.hillfort.location.lng}"

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, details);
            startActivity(Intent.createChooser(shareIntent,"Share via"))
        }

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                setDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
                toast(sdf.format(cal.time))
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        datePicker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@HillfortView,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        notes.setText(hillfort.notes)
        visited_checkbox.isChecked
        setDate.setText(hillfort.date)
        notes.setText(hillfort.notes)
        ratingBar.setRating(hillfort.rating)

        Glide.with(this).load(hillfort.image).into(hillfortImage);
        if (hillfort.image != null) {
            chooseImage.setText(R.string.change_hillfort_image)
        }
        this.showLocation(hillfort.location)
        if (hillfort.favourite == true) {
            fav_button.setBackgroundResource(fav_black)
        } else if (hillfort.favourite == false) {
            fav_button.setBackgroundResource(fav_white)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(
                        hillfortTitle.text.toString(),
                        description.text.toString(),
                        visited_checkbox.isChecked,
                        setDate.text.toString(),
                        notes.text.toString(),
                        ratingBar.getRating(),
                        hillfort.favourite

                    )
                }
            }

            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.action_add -> startActivity<HillfortView>()

            R.id.action_close -> finishAffinity()
        }
        return true
    }

    override fun showLocation(location: Location) {
        latVal.setText("%.6f".format(location.lat))
        longVal.setText("%.6f".format(location.lng))
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}



















