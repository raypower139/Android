package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel


class SettingsActivity : AppCompatActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

  lateinit var app: MainApp
  private val drawerLayout by lazy {
    findViewById<DrawerLayout>(R.id.drawer_layout)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_nav)
    app = application as MainApp

    toolbarSettings.title = title
    toolbarSettings.setSubtitle("${app.currentUser.name}")
    setSupportActionBar(toolbarSettings)
    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

    val navView = findViewById<NavigationView>(R.id.nav_view)
    navView.setNavigationItemSelectedListener(this)

    showName.setText(app.currentUser.name)
    showEmail.setText(app.currentUser.email)
    showPassword.setText(app.currentUser.password)

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivity<HillfortActivity>()
      R.id.item_logout -> startActivity<LoginActivity>()
      R.id.action_close -> finishAffinity()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    drawerLayout.closeDrawer(GravityCompat.START)
    when (item.itemId) {
      R.id.action_add -> startActivity<HillfortActivity>()
      R.id.action_logout -> startActivity<LoginActivity>()
      R.id.action_close -> finishAffinity()
    }
    return true

  }
}

