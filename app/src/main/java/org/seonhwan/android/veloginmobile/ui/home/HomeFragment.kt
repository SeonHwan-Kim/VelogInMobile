package org.seonhwan.android.veloginmobile.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding
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
                    with(binding) {
                        addToolbarTag("", R.drawable.ic_plus, 0, false)
                        addToolbarTag("트렌드", null, 1, true)
                        addToolbarTag("팔로우", null, 2, false)
                        viewModel.tagList.value?.mapIndexed { index, tag ->
                            addToolbarTag(tag, null, index + 3, false)
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

    private fun addToolbarTag(tag: String, icon: Int?, position: Int, setSelected: Boolean) {
        with(binding) {
            val newTab = tabHomeTabbar.newTab()
            newTab.text = tag
            if (icon != null) {
                newTab.icon = ContextCompat.getDrawable(requireContext(), icon)
            }
            tabHomeTabbar.addTab(newTab, position, setSelected)
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
                        moveToAddTag()
                    }

                    "트렌드" -> {
                    }

                    "팔로우" -> {
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

    private fun moveToAddTag() {
        val intent = Intent(activity, AddTagActivity::class.java)
        getResultSignUp.launch(intent)
        startSecondTabItem()
    }

    private val getResultSignUp = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            binding.tabHomeTabbar.removeAllTabs()
            viewModel.getTag()
        }
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
