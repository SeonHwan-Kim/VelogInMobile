package org.seonhwan.android.veloginmobile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityMainBinding
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment
import org.seonhwan.android.veloginmobile.ui.Subscribe.SubscribeFragment
import org.seonhwan.android.veloginmobile.ui.home.HomeFragment
import org.seonhwan.android.veloginmobile.ui.mypage.MypageFragment
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.initLayout()
    }

    private fun initLayout() {
        binding.bnvMain.itemIconTintList = null

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()
        }
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> this.changeFragment(HomeFragment())
                R.id.menu_list -> this.changeFragment(SubscribeFragment())
                R.id.menu_bookmark -> this.changeFragment(ScrapFragment())
                else -> this.changeFragment(MypageFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcv_main, fragment).commit()
    }
}
