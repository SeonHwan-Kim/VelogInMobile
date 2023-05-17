package org.seonhwan.android.veloginmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.seonhwan.android.veloginmobile.databinding.ActivityMainBinding
import org.seonhwan.android.veloginmobile.ui.Notification.NotificationFragment
import org.seonhwan.android.veloginmobile.ui.Subscribe.SubscribeFragment
import org.seonhwan.android.veloginmobile.ui.home.HomeFragment
import org.seonhwan.android.veloginmobile.ui.mypage.MypageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.initLayout()
    }

    private fun initLayout(){
        binding.bnvMain.itemIconTintList = null

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if(currentFragment == null){
            supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()
        }
        binding.bnvMain.setOnItemSelectedListener {item ->
            when (item.itemId) {
                R.id.menu_home -> this.changeFragment(HomeFragment())
                R.id.menu_subscribe -> this.changeFragment(SubscribeFragment())
                R.id.menu_notification -> this.changeFragment(NotificationFragment())
                else -> this.changeFragment(MypageFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}