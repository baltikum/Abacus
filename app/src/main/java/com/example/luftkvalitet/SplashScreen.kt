package com.example.luftkvalitet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luftkvalitet.databinding.ActivityMainBinding
import com.example.luftkvalitet.databinding.ActivitySplashScreenBinding

/* "Loadingscreen" when opening app for the first time
 *
 */
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivAir.alpha = 0f;   //Get the image and make it transparent
        binding.ivAir.animate().setDuration(2000).alpha(1f).withEndAction {     //Animation for 2 seconds: image -> make visible
            val i = Intent(this, MainActivity::class.java)
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}