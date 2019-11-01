package org.wit.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.models.generateRandomId

class SignUpActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp
        info("Login Activity Started...")

        btnSignUp.setOnClickListener {


            if (signupName != null && signupPassword != null) {

                val result = app.users.findByName(signupName.text.toString())

                if (result == null) {

                    app.users.create(
                        UserModel(
                            generateRandomId(),
                            signupName.text.toString(),
                            signupPassword.text.toString()
                        )
                    )
                    toast("user created")
                    startActivity(intentFor<LoginActivity>())

                    val resultLogin =
                        app.users.login(signupName.text.toString(), signupPassword.text.toString())
                    if (resultLogin) {
                        var currentUser = app.users.findByName(signupName.text.toString())
                        if (currentUser != null) {
                            app.currentUser = currentUser
                            startActivity(intentFor<HillfortListActivity>())
                        }

                    } else (toast("A user with that name already exists, please choose another"))
                }
            }
        }
    }}