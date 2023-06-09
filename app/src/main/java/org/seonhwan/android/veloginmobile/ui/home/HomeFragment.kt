package org.seonhwan.android.veloginmobile.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.data.local.model.toPost
import org.seonhwan.android.veloginmobile.databinding.FragmentHomeBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagActivity
import org.seonhwan.android.veloginmobile.ui.home.HomeViewModel.Companion.CODE_202
import org.seonhwan.android.veloginmobile.ui.home.HomeViewModel.Companion.NETWORK_ERR
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment.Companion.FOLDER_NAME
import org.seonhwan.android.veloginmobile.ui.webview.WebViewActivity
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import org.seonhwan.android.veloginmobile.util.extension.showToast
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var postAdapter: VelogAdapter? = null
    private var scrapPostList: List<ScrapPost>? = null

    private var tabClickJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabItem()
        initAdapter()
        initPost()
        startSecondTabItem()
        onClickTabBar()
        getAllScrapPost()
    }

    private fun initTabItem() {
        viewModel.tagList.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    with(binding) {
                        addToolbarTag("", R.drawable.ic_plus, 0, false)
                        addToolbarTag("트렌드", null, 1, true)
                        addToolbarTag("팔로우", null, 2, false)
                        event.data.mapIndexed { index, tag ->
                            addToolbarTag(tag, null, index + 3, false)
                        }
                    }
                }

                is Failure -> {
                    when (event.code) {
                        NETWORK_ERR -> requireActivity().showToast("네트워크 상태를 확인해주세요")
                        else -> requireActivity().showToast("문제가 발생하였습니다")
                    }
                    Timber.tag("tagListState").e("Failure")
                }
            }
        }.launchIn(lifecycleScope)
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
        postAdapter = VelogAdapter(
            requireActivity() as AppCompatActivity,
            { post ->
                intentToWebView(post)
            },
            { post ->
                viewModel.scrapPost(post, null)
            },
            { post ->
                viewModel.deleteScrapPost(post, getScrapPostFolder(post.url))
            },
            scrapPostList?.map { scrapPost -> scrapPost.toPost() },
        )
        binding.rvHomePost.adapter = postAdapter
    }

    private fun intentToWebView(post: Post) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra(VELOG, post)
        intent.putExtra(FOLDER_NAME, getScrapPostFolder(post.url))
        getResultSubscribe.launch(intent)
    }

    private fun onClickTabBar() {
        binding.tabHomeTabbar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabClickJob?.cancel()

                tabClickJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(500)

                    when (tab?.text) {
                        "" -> {
                            moveToAddTag()
                        }

                        "트렌드" -> {
                            viewModel.getTrendPost()
                            initAdapter()
                        }

                        "팔로우" -> {
                            viewModel.getSubscriberPost()
                            initAdapter()
                        }

                        else -> {
                            viewModel.getTagPost(tab?.text.toString())
                            initAdapter()
                        }
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
            val selectedTab =
                binding.tabHomeTabbar.getTabAt(binding.tabHomeTabbar.selectedTabPosition)
            val selectedTabName = selectedTab?.text?.toString()
            when (binding.tabHomeTabbar.selectedTabPosition) {
                1 -> viewModel.getTrendPost()
                2 -> viewModel.getSubscriberPost()
                else -> viewModel.getTagPost(selectedTabName!!)
            }
        }
    }

    private fun initPost() {
        viewModel.postList.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {
                    binding.ivHomeNoSubscriber.visibility = View.GONE
                    binding.pbHomeLoading.visibility = View.VISIBLE
                }

                is Success -> {
                    binding.pbHomeLoading.visibility = View.GONE
                    binding.ivHomeNoSubscriber.visibility = View.GONE
                    postAdapter?.submitList(event.data)
                }

                is Failure -> {
                    binding.ivHomeNoSubscriber.visibility = View.VISIBLE
                    when (event.code) {
                        CODE_202 -> {
                            binding.pbHomeLoading.visibility = View.GONE
                        }

                        else -> {
                            binding.pbHomeLoading.visibility = View.GONE
                            requireActivity().showToast("문제가 발생하였습니다")
                        }
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun getAllScrapPost() {
        viewModel.getAllScrapPostState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    scrapPostList = event.data
                }

                is Failure -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun getScrapPostFolder(postUrl: String): String? {
        scrapPostList?.map { scrapPost ->
            if (scrapPost.url == postUrl) {
                return scrapPost.folder
            }
        }
        return null
    }

    fun scrollToTop() {
        binding.rvHomePost.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postAdapter = null
        scrapPostList = null
        tabClickJob = null
    }
}
