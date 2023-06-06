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
import org.seonhwan.android.veloginmobile.ui.home.HomeViewModel.Companion.CODE_202
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.ui.webview.WebViewActivity
import org.seonhwan.android.veloginmobile.util.UiState.Error
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import org.seonhwan.android.veloginmobile.util.extension.showToast
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var postAdapter: VelogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabItem()
        initAdapter()
        initPost()
        startSecondTabItem()
        onClickTabBar()
    }

    private fun initTabItem() {
        viewModel.tagListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success -> {
                    with(binding) {
                        addToolbarTag("", R.drawable.ic_plus, 0, false)
                        addToolbarTag("트렌드", null, 1, true)
                        addToolbarTag("팔로우", null, 2, false)
                        viewModel.tagList.value?.mapIndexed { index, tag ->
                            addToolbarTag(tag, null, index + 3, false)
                        }
                    }
                }

                is Failure -> {
                    Timber.tag("tagListState").e("Failure")
                }

                is Error -> {
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
        postAdapter = VelogAdapter() { post ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(VELOG, post)
            getResultSubscribe.launch(intent)
        }
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
                        viewModel.getTagPost()
                        initAdapter()
                    }

                    "팔로우" -> {
                        viewModel.getSubscriberPost()
                        initAdapter()
                    }

                    else -> {
                        viewModel.getTagPost()
                        initAdapter()
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
        viewModel.getTagPost()
    }

    private fun moveToAddTag() {
        val intent = Intent(activity, AddTagActivity::class.java)
        getResultAddTag.launch(intent)
        startSecondTabItem()
    }

    private val getResultAddTag = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            binding.tabHomeTabbar.removeAllTabs()
            viewModel.getTag()
        }
    }

    private val getResultSubscribe = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            viewModel.getTagPost()
        }
    }

    private fun initPost() {
        viewModel.postListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success -> {
                    postAdapter?.submitList(viewModel.postList.value)
                }

                is Failure -> {
                    when (state.code) {
                        CODE_202 -> {
                            requireActivity().showToast("구독한 사람이 없습니다")
                        }

                        else -> {
                            requireActivity().showToast("Failure")
                        }
                    }
                }

                is Error -> {
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postAdapter = null
    }
}
