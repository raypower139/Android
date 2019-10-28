package org.wit.hillfort.activities

import android.content.Intent
import android.os.Handler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfort.R
import org.wit.hillfort.activities.HillfortListActivity

class SplashActivity : AppCompatActivity() {

    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 2000  //splash screen will be shown for 2 seconds

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, HillfortListActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}