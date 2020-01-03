package org.wit.hillfort.views.hillfortFavouritesList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.startActivityForResult

import org.wit.hillfort.R
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.hillfort.HillfortView
import org.wit.hillfort.views.hillfortList.HillfortAdapter
import org.wit.hillfort.views.hillfortList.HillfortListView
import org.wit.hillfort.views.hillfortList.HillfortListener
import org.wit.hillfort.views.map.HillfortMapView

class HillfortFavouriteListView : BaseView(),
  HillfortListener, NavigationView.OnNavigationItemSelectedListener {

  lateinit var presenter: HillfortFavouriteListPresenter

  private val drawerLayout by lazy {
    findViewById<DrawerLayout>(R.id.drawer_layout)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_nav)
    toolbar.title = "Favourites"
    //toolbar.setSubtitle("${app.currentUser.name}")
    setSupportActionBar(toolbar)
    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

    presenter = initPresenter(
      HillfortFavouriteListPresenter(
        this
      )
    ) as HillfortFavouriteListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadFavouriteHillforts()


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
    recyclerView.adapter =
      HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddHillfort()
      R.id.item_map -> presenter.doShowHillfortsMap()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadFavouriteHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    drawerLayout.closeDrawer(GravityCompat.START)
    when (item.itemId) {
      R.id.action_add -> presenter.doAddHillfort()
      R.id.action_fav -> presenter.doShowFavouriteHillfort()
      R.id.item_map -> {startActivityForResult<HillfortMapView>(0)
        overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);}
      R.id.item_logout ->presenter.doLogout()
      R.id.action_close -> finish()
    }
    return true
  }

  private val mOnNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.nav_home -> {startActivityForResult<HillfortListView>(0)
          overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);}
        R.id.nav_add -> {startActivityForResult<HillfortView>(0)
          overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);}
        R.id.nav_map -> {startActivityForResult<HillfortFavouriteListView>(0)
          overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);}
      }
      false
    }
}

