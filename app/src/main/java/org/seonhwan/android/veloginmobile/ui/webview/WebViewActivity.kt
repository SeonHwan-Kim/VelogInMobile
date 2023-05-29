package org.seonhwan.android.veloginmobile.ui.webview

import android.os.Bundle
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityWebViewBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.presentation.home.VelogAdapter.Companion.VELOG
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.getParcelable

class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        webViewSetting()
        startWebView()
    }

    private fun webViewSetting() {
        binding.wvWebview.settings.apply {
            javaScriptEnabled = true
        }
        binding.wvWebview.isVerticalScrollBarEnabled = false
    }

    private fun startWebView() {
        val post = intent.getParcelable(VELOG, Post::class.java)
        binding.wvWebview.loadUrl("https://velog.io$post.url")
    }
}
