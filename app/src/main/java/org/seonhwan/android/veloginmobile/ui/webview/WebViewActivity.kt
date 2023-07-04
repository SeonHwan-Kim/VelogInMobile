package org.seonhwan.android.veloginmobile.ui.webview

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityWebViewBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.getParcelable
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    private val viewModel by viewModels<WebViewViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSubscribeButton()
        onClickToolbarBackButton()
        onClickToolbarSubscribeButton()
        onClickToolbarBookmarkButton()
        webViewSetting()
        startWebView()
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
        with(binding.ibWebviewBookmark) {
            setOnClickListener {
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
