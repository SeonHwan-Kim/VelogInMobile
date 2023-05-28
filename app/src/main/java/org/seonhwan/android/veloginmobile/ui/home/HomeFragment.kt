package org.seonhwan.android.veloginmobile.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding
import org.seonhwan.android.veloginmobile.presentation.home.VelogAdapter
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var postAdapter: VelogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        startSecondTabItem()
        onClickTabBar()
        viewModel.getTag()
    }

    private fun initAdapter() {
        postAdapter = VelogAdapter()
        binding.rvHomePost.adapter = postAdapter
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
        viewModel.tagPostListState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    postAdapter?.submitList(viewModel.tagPostList.value)
                }

                is UiState.Failure -> {
                    Timber.tag("tagPostListState").e("Failure")
                }

                is UiState.Error -> {
                    Timber.tag("tagPostListState").e("Error")
                }
            }
        }
        viewModel.tagPostList.observe(this) {
            binding.rvHomePost.adapter = VelogAdapter().apply {
                submitList(it)
            }
        }
    }
}

// addtab 사용하자
