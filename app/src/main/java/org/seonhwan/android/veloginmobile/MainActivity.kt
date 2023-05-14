package org.seonhwan.android.veloginmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.seonhwan.android.veloginmobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvMain.itemIconTintList = null
    }
}