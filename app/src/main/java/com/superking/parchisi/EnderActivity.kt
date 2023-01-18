package com.superking.parchisi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.superking.parchisi.databinding.ActivityEnderBinding

class EnderActivity : AppCompatActivity() {
    private lateinit var iiiinfd: ActivityEnderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        iiiinfd = ActivityEnderBinding.inflate(layoutInflater)
        setContentView(iiiinfd.root)



        iiiinfd.btnStart.setOnClickListener {

            iiiinfd.imageView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ffrggss))
            iiiinfd.imageView2.animate().alpha(0f).duration = 1700

            Handler(Looper.getMainLooper()).postDelayed({
                val ffdddsxv = Intent(this, Prodigy::class.java)
                startActivity(ffdddsxv)
                finish()
            }, 1750)

        }
    }
}