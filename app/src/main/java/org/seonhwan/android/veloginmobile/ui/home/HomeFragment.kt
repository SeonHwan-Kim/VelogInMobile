package org.seonhwan.android.veloginmobile.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel>()
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "_binding is not null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startSecondTabItem()
        onClickTabBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickTabBar() {
        binding.tabHomeTabbar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "" -> {
//                        binding.tabHomeTabbar.setSelectedTabIndicatorColor(resources.getColor(R.color.transparent))
                        val intent = Intent(activity, AddTagActivity::class.java)
                        startActivity(intent)
                        startSecondTabItem()
                    }

                    else -> {
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
}

// addtab 사용하자
