package org.wit.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp

class LoginActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp
        info("Login Activity Started...")


        //btnSignUp.setOnClickListener() {

            //startActivity(intentFor<SignUpActivity>())
        //}

        btnLogin.setOnClickListener() {

            // Basic validation for input fields
            if (loginName.text.toString().isEmpty()){
                toast("Please enter your name.")
            } else if (loginPassword.text.toString().isEmpty()){
                toast("Please enter a password.")
            }

            // Check the users details
            var credentials = app.users.login(loginName.text.toString(), loginPassword.text.toString())

            if (credentials){
                //Find user by Name
                var currentUser = app.users.findByName(loginPassword.text.toString())

                //Start and Display Hillfort List
                if (currentUser != null){
                    app.currentUser = currentUser
                    startActivity(intentFor<HillfortListActivity>())
                }

            }else (toast("Please enter valid name and password"))
        }
        }

}