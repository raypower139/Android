package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortListActivity : AppCompatActivity(), HillfortListener, NavigationView.OnNavigationItemSelectedListener {

  lateinit var app: MainApp

  private val drawerLayout by lazy {
    findViewById<DrawerLayout>(R.id.drawer_layout)
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_nav)
    app = application as MainApp

    toolbar.title = title
    //toolbar.setSubtitle("${app.currentUser.name}")
    setSupportActionBar(toolbar)

    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)

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
      R.id.item_add -> startActivity<HillfortActivity>()
      R.id.item_map -> startActivity<HillfortMapsActivity>()


      R.id.action_close -> finishAffinity()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    drawerLayout.closeDrawer(GravityCompat.START)
    when (item.itemId) {
      R.id.action_add -> startActivity<HillfortActivity>()


      R.id.action_close -> finishAffinity()
    }
    return true
  }
}

