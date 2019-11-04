package org.wit.hillfort.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel


class SettingsActivity : AppCompatActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

  lateinit var app: MainApp

  // LAYOUT FOR NAVIGATION DRAWER
  private val drawerLayout by lazy {
    findViewById<DrawerLayout>(R.id.drawer_layout)
  }
  private var updateUser = UserModel()

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

    // SET FIELD VALUES
      showName.setText(app.currentUser.name)
      showEmail.setText(app.currentUser.email)
      showPassword.setText(app.currentUser.password)
      numberOfHillforts.setText("Number of Hillforts: " + app.users.findAllHillfort(app.currentUser).size)
      numberOfHillfortsVisited.setText("Number of Hillforts Visited:      " + app.currentUser.hillforts.filter { it.visited }.size)


    // UPDATE USER
    updateUserButton.setOnClickListener() {
      updateUser.id = app.currentUser.id
      updateUser.name = showName.text.toString()
      updateUser.email = showEmail.text.toString()
      updateUser.password = showPassword.text.toString()
      if (updateUser.name.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
          app.users.updateUser(updateUser.copy())
        toast("User Updated")
      }
    }

    // DELETE USER
    deleteUserButton.setOnClickListener() {

        val alertDialog = AlertDialog.Builder(this)
          //set icon
          .setIcon(android.R.drawable.ic_dialog_alert)
          //set title
          .setTitle("Are you sure you want to DELETE")
          //set message
          .setMessage("If yes then User will be deleted")
          //set positive button
          .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->
            app.users.delete(app.currentUser)
            toast("User Deleted")
            startActivity<LoginActivity>()
            finish()
          })
          //set negative button
          .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            Toast.makeText(applicationContext, "User will not be deleted", Toast.LENGTH_LONG).show()
          })
          .show()
      }

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

  fun findAllHillfort(user: UserModel): ArrayList<HillfortModel> {
    return user.hillforts
  }




}

