package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel



@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SettingsActivity : AppCompatActivity(), AnkoLogger {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    app = application as MainApp

    toolbarSettings.title = title
    toolbarSettings.setSubtitle("${app.currentUser.name}")
    setSupportActionBar(toolbarSettings)
    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

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
      R.id.item_logout -> finish()
    }
    return super.onOptionsItemSelected(item)
  }

}

