package org.wit.hillfort.views.hillfortList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*

import org.wit.hillfort.R
import org.wit.hillfort.models.HillfortModel

class HillfortListView : AppCompatActivity(),
  HillfortListener, NavigationView.OnNavigationItemSelectedListener {

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

    presenter = HillfortListPresenter(this)
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = HillfortAdapter(
      presenter.getHillforts(),
      this
    )
    recyclerView.adapter?.notifyDataSetChanged()
    val navView = findViewById<NavigationView>(R.id.nav_view)
    navView.setNavigationItemSelectedListener(this)

    val toggle = ActionBarDrawerToggle(
       this, drawerLayout,toolbar,
        R.string.open_nav_drawer, R.string.close_nav_drawer
    )
          drawerLayout.addDrawerListener(toggle)
          toggle.syncState()
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
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    drawerLayout.closeDrawer(GravityCompat.START)
    when (item.itemId) {
      R.id.action_add -> presenter.doAddHillfort()
    }
    return true
  }
}

