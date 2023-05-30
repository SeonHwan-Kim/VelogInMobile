package org.seonhwan.android.veloginmobile.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding
import org.seonhwan.android.veloginmobile.presentation.home.VelogAdapter
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagActivity
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var postAdapter: VelogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabItem()
        initAdapter()
        startSecondTabItem()
        onClickTabBar()
    }

    private fun initTabItem() {
        viewModel.tagListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    viewModel.tagList.value?.map { tag ->
                        with(binding) {
                            val newTab = tabHomeTabbar.newTab()
                            newTab.text = tag
                            tabHomeTabbar.addTab(newTab)
                            Timber.tag("tagListState").d(tag)
                        }
                    }
                }

                is UiState.Failure -> {
                    Timber.tag("tagListState").e("Failure")
                }

                is UiState.Error -> {
                    Timber.tag("tagListState").e("Error")
                }
            }
        }
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
                        val intent = Intent(activity, AddTagActivity::class.java)
                        startActivity(intent)
                        startSecondTabItem()
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
        initPost()
    }

    private fun initPost() {
        viewModel.getTagPost()
        viewModel.tagPostListState.observe(viewLifecycleOwner) { state ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postAdapter = null
    }
}
