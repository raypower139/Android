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
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BaseView
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
        setContentView(R.layout.activity_hillfort_nav)
        init(toolbarAdd)
        //getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        info("Hillfort Activity started..")

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)


        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val ratingButton = findViewById<Button>(R.id.ratingButton)
        ratingButton?.setOnClickListener {
            val msg = ratingBar.rating.toString()
            Toast.makeText(
                this@HillfortView,
                "Rating is: " + msg, Toast.LENGTH_SHORT
            ).show()}

            //  SET IMAGE AND CALENDER PICKER TO INVISIBLE
            hillfortImage.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.INVISIBLE);
            dateText.setVisibility(View.INVISIBLE);
            setDate.setVisibility(View.INVISIBLE);

            chooseImage.setOnClickListener { presenter.doSelectImage() }
            hillfortLocation.setOnClickListener { presenter.doSetLocation() }

            visited_checkbox.setOnClickListener(View.OnClickListener {
                if (visited_checkbox.isChecked) {
                    hillfort.visited = true
                    datePicker.setVisibility(View.VISIBLE); //Set to visible
                    dateText.setVisibility(View.VISIBLE); //Set to visible
                    setDate.setVisibility(View.VISIBLE); //Set to visible
                } else {
                    hillfort.visited = false //Set to invisible
                    datePicker.setVisibility(View.INVISIBLE); //Set to invisible
                    dateText.setVisibility(View.INVISIBLE); //Set to invisible
                    setDate.setVisibility(View.INVISIBLE); //Set to invisible
                }
            })

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
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if (hillfort.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
            latVal.setText("" + hillfort.lat)
            longVal.setText("" + hillfort.lng)
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
                            ratingBar.getRating()
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

    }




















