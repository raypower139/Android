package org.wit.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btnSignUp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserJSONStore
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.models.generateRandomUserId

class SignUpActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp
    var valid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp
        info("Login Activity Started...")
        btnSignUp.setOnClickListener {

            if (signupName.text.toString().length < 5) {
                toast("Please enter your name, must be 5 characters or more..")
            } else if (signupEmail.text.toString().length < 5) {
                toast("Please enter a password, must be 5 characters or more.")
            } else if (signupPassword.text.toString().length < 5) {
                toast("Please enter a password, must be 5 characters or more.")
            }
            else {
                valid = true
            }

            if (valid != false){
                val result = app.users.findByEmail(signupEmail.text.toString())

                if (result == null) {

                    app.users.create(
                        UserModel(
                            generateRandomUserId(),
                            signupName.text.toString(),
                            signupEmail.text.toString(),
                            signupPassword.text.toString()
                        )

                    )
                    toast("user created")
                    startActivity(intentFor<LoginActivity>())

                    val resultLogin =
                        app.users.login(signupEmail.text.toString(), signupPassword.text.toString())
                    if (resultLogin) {
                        var currentUser = app.users.findByEmail(signupEmail.text.toString())
                        if (currentUser != null) {
                            app.currentUser = currentUser
                            app.users.createHillfort(app.currentUser,HillfortModel(1,title = "Annestown", description = "The site is located immediately S of Annestown Village", lat = 52.134562, lng = -7.080937 ))
                            app.users.createHillfort(app.currentUser,HillfortModel(2,title = "Kilfarrasy", description = "This coastal promontory is located c. 2km W of Annestown in Co. Waterford.", lat = 52.133597, lng = -7.243725 ))
                            app.users.createHillfort(app.currentUser,HillfortModel(3,title = "Islandikane East", description = "This coastal promontory, depicted as the site of an 'ancient irish dwelling' on the first edition OS map", lat = 52.132271, lng = -7.219559 ))


                            startActivity(intentFor<HillfortListActivity>())
                        }

                    } else (toast("A user with that email already exists, please choose another"))
                }
            }
        }
    }}