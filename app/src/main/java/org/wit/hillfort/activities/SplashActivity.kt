package org.wit.hillfort.activities

import android.content.Intent
import android.os.Handler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfort.R
import org.wit.hillfort.views.login.LoginView

class SplashActivity : AppCompatActivity() {

    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 4000  //splash screen will be shown for 4 seconds

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.splash)

        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, LoginView::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}