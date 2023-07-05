package org.seonhwan.android.veloginmobile.ui.webview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityWebViewBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.HomeViewModel
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment.Companion.FOLDER_NAME
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.BookmarkSnackbar
import org.seonhwan.android.veloginmobile.util.extension.getParcelable
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    private val viewModel by viewModels<WebViewViewModel>()
    private val scrapViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initScrapButton()
        initSubscribeButton()
        onClickToolbarBackButton()
        onClickToolbarSubscribeButton()
        onClickToolbarBookmarkButton()
        webViewSetting()
        startWebView()
    }

    private fun initScrapButton() {
        viewModel.isScrapPostState.flowWithLifecycle(lifecycle).onEach { isScrapPost ->
            if (isScrapPost) {
                binding.ibWebviewBookmark.isSelected = true
            }
        }.launchIn(lifecycleScope)
    }

    private fun onClickToolbarBackButton() {
        binding.ibWebviewBackButton.setOnClickListener {
            if (!isFinishing) finish()
        }
    }

    private fun onClickToolbarSubscribeButton() {
        with(binding.ibWebviewSubscribe) {
            setOnClickListener {
                val post = intent.getParcelable(VELOG, Post::class.java)
                if (isSelected) {
                    viewModel.deleteSubscriber(post?.name!!)
                    showToast("${post.name}님의 구독을 취소하였습니다")
                } else {
                    viewModel.addSubscriber(post?.name!!)
                    showToast("${post.name}님을 구독하였습니다")
                }
                isSelected = !isSelected
                setResult(RESULT_OK)
            }
        }
    }

    private fun onClickToolbarBookmarkButton() {
        val post = intent.getParcelable(VELOG, Post::class.java)
        val folderName = intent.getStringExtra(FOLDER_NAME)
        viewModel.isScrapPost(post?.url!!)
        with(binding.ibWebviewBookmark) {
            setOnClickListener {
                if (isSelected) {
                    scrapViewModel.deleteScrapPost(post!!, folderName)
                } else {
                    scrapViewModel.scrapPost(post!!, null)
                    org.seonhwan.android.veloginmobile.util.extension.BookmarkSnackbar.make(
                        post,
                        this@WebViewActivity,
                        binding.root,
                        "스크랩 했습니다.",
                    ).show()
                }
                isSelected = !isSelected
            }
        }
    }

    private fun webViewSetting() {
        binding.wvWebview.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
    }

    private fun startWebView() {
        val post = intent.getParcelable(VELOG, Post::class.java)
        binding.wvWebview.loadUrl("${post?.url}")
    }

    private fun initSubscribeButton() {
        with(binding) {
            val post = intent.getParcelable(VELOG, Post::class.java)
            ibWebviewSubscribe.isSelected = post?.isSubscribed!!
        }
    }
}
