package org.seonhwan.android.veloginmobile.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding
import org.seonhwan.android.veloginmobile.presentation.home.VelogAdapter
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startSecondTabItem()
        onClickTabBar()
        viewModel.getTag()
    }

    private fun onClickTabBar() {
        binding.tabHomeTabbar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "" -> {
//                        binding.tabHomeTabbar.setSelectedTabIndicatorColor(resources.getColor(R.color.transparent))
//                        val intent = Intent(activity, AddTagActivity::class.java)
//                        startActivity(intent)
//                        startSecondTabItem()
                        initPost()
                    }
                    else -> {
                        initPost()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }
        })
    }

    private fun startSecondTabItem() {
        binding.tabHomeTabbar.getTabAt(1)?.select()
    }

    private fun initPost() {
        viewModel.getTagPost()
        viewModel.tagPostList.observe(this) {
            binding.rvHomePost.adapter = VelogAdapter().apply {
                submitList(it)
            }
        }
    }
}

// addtab 사용하자
