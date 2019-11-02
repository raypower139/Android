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
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.models.generateRandomId

class SignUpActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp
    var valid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp
        info("Login Activity Started...")
        btnSignUp.setOnClickListener {

            if (signupName.text.toString().isEmpty()|| signupName.text.toString().length < 5) {
                toast("Please enter your name, must be 3 characters or more..")
            } else if (signupEmail.text.toString().isEmpty()|| signupEmail.text.toString().length < 5) {
                toast("Please enter a password, must be 6 characters or more.")
            } else if (signupPassword.text.toString().isEmpty()|| signupPassword.text.toString().length < 5) {
                toast("Please enter a password, must be 6 characters or more.")
            }
            else {
                valid = true
            }

            if (valid != false){
                //signupName != null && signupEmail != null && signupPassword != null) {

                val result = app.users.findByEmail(signupEmail.text.toString())

                if (result == null) {

                    app.users.create(
                        UserModel(
                            generateRandomId(),
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
                            startActivity(intentFor<HillfortListActivity>())
                        }

                    } else (toast("A user with that email already exists, please choose another"))
                }
            }
        }
    }}