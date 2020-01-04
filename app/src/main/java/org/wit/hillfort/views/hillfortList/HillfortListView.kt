package org.wit.hillfort.views.hillfortList

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_hillfort_list.toolbar
import kotlinx.android.synthetic.main.activity_hillfort_nav.nav_view
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.card_hillfort.*

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.R.anim.*
import org.wit.hillfort.activities.Settings
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.json.app
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.hillfort.HillfortView
import org.wit.hillfort.views.hillfortFavouritesList.HillfortFavouriteListView
import org.wit.hillfort.views.map.HillfortMapView


class HillfortListView : BaseView() , HillfortListener, NavigationView.OnNavigationItemSelectedListener {

  lateinit var presenter: HillfortListPresenter

  private val drawerLayout by lazy {
    findViewById<DrawerLayout>(R.id.drawer_layout)

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_nav)
    toolbar.title = title
    //toolbar.setSubtitle("${app.currentUser.name}")
    setSupportActionBar(toolbar)
    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

    presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadHillforts()

    val navView = findViewById<NavigationView>(R.id.nav_view)
    navView.setNavigationItemSelectedListener(this)

    val navigation: BottomNavigationView = findViewById(R.id.bottomNav) as BottomNavigationView
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    val toggle = ActionBarDrawerToggle(
       this, drawerLayout,toolbar,
        R.string.open_nav_drawer, R.string.close_nav_drawer
    )
          drawerLayout.addDrawerListener(toggle)


          toggle.syncState()
    }

  override fun showHillforts(hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
    val user = FirebaseAuth.getInstance().currentUser
    val navHeaderEmail = nav_view.getHeaderView(0).findViewById(R.id.nav_header_email) as TextView
    navHeaderEmail.text = user?.email
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      //R.id.item_add -> presenter.doAddHillfort()
      R.id.item_map -> presenter.doShowHillfortsMap()
      R.id.item_logout ->presenter.doLogout()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    drawerLayout.closeDrawer(GravityCompat.START)
    when (item.itemId) {
      R.id.action_add -> presenter.doAddHillfort()
      R.id.action_fav -> presenter.doShowFavouriteHillfort()
      R.id.item_map -> {startActivityForResult<HillfortMapView>(0)
        overridePendingTransition(R.anim.slide_in_up, no_anim);}
      R.id.item_settings -> {startActivityForResult<Settings>(0)
        overridePendingTransition(R.anim.slide_in_up, no_anim);}
      R.id.item_logout ->presenter.doLogout()
        R.id.action_close -> {finishAffinity()
            overridePendingTransition(no_anim, slide_out_down);}
    }
    return true
  }

  private val mOnNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.nav_home -> {startActivityForResult<HillfortListView>(0)
        overridePendingTransition(slide_in_up, no_anim);}
        R.id.nav_add -> {startActivityForResult<HillfortView>(0)
          overridePendingTransition(slide_in_up, no_anim);}
        R.id.nav_map -> {startActivityForResult<HillfortFavouriteListView>(0)
          overridePendingTransition(slide_in_up, no_anim);}

      }
      false
    }



}

