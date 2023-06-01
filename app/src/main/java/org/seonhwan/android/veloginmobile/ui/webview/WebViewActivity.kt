package org.seonhwan.android.veloginmobile.ui.webview

import android.os.Bundle
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityWebViewBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.getParcelable

class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                isSelected = !isSelected
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
        binding.wvWebview.loadUrl("https://velog.io${post?.url}")
    }
}
