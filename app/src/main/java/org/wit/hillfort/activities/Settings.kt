package org.wit.hillfort.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.R.anim.*
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.hillfort.HillfortView
import org.wit.hillfort.views.hillfortFavouritesList.HillfortFavouriteListView
import org.wit.hillfort.views.login.LoginView
import org.wit.hillfort.views.map.HillfortMapView


class Settings : AppCompatActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {



    // LAYOUT FOR NAVIGATION DRAWER
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }
    var hillfort = HillfortModel()
    lateinit var app: MainApp
    private val calsBurned = 0
    private val calsConsumed = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val user = FirebaseAuth.getInstance().currentUser
        app = application as MainApp

        //val navView = findViewById<NavigationView>(R.id.nav_view)
       // navView.setNavigationItemSelectedListener(this)

        // SET FIELD VALUES
        showEmail.setText("User Email: " + user?.email)
        numberOfHillforts.setText("Number of Hillforts: " + app.hillforts.findAll().size )
        numberOfHillfortsVisited.setText("Number of Hillforts Visited: " + app.hillforts.findAll().filter { it.visited }.size)
        numberOfFavourtieHillforts.setText("Number of Favourties: " + app.hillforts.findAll().filter { it.favourite }.size)







    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_map -> {startActivityForResult<HillfortMapView>(0)
                overridePendingTransition(slide_in_up, no_anim);}
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.action_add -> {startActivityForResult<HillfortView>(0)
                overridePendingTransition(slide_in_up, no_anim);}
            R.id.action_fav -> {startActivityForResult<HillfortFavouriteListView>(0)
                overridePendingTransition(slide_in_up, no_anim);}
            R.id.item_map -> {startActivityForResult<HillfortMapView>(0)
                overridePendingTransition(slide_in_up, no_anim);}
            R.id.item_logout -> doLogout()
            R.id.action_close -> {finishAffinity()
                overridePendingTransition(no_anim, slide_out_down);}
        }
        return true
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        startActivityForResult<LoginView>(0)

    }


}

