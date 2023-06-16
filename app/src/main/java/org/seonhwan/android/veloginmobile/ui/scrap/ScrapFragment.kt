package org.seonhwan.android.veloginmobile.ui.scrap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.data.local.model.toPost
import org.seonhwan.android.veloginmobile.databinding.FragmentScrapBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter
import org.seonhwan.android.veloginmobile.ui.webview.WebViewActivity
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class ScrapFragment : BindingFragment<FragmentScrapBinding>(R.layout.fragment_scrap) {
    private val viewModel by viewModels<ScrapViewModel>()
    private var postAdapter: VelogAdapter? = null
    private var scrapPostList: List<Post>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrapPost()
        initAdapter()
    }

    private fun initAdapter() {
        postAdapter = VelogAdapter(
            { post ->
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra(VelogAdapter.VELOG, post)
                getResultSubscribe.launch(intent)
            },
            { post ->
                viewModel.scrapPost(post, null)
            },
            { url ->
                viewModel.deleteScrapPost(url)
            },
            scrapPostList,
        )

        binding.rvScrapPost.adapter = postAdapter
    }

    private val getResultSubscribe = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getAllScrapPost()
        }
    }

    private fun initScrapPost() {
        viewModel.getAllScrapPostState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> binding.pbScrapLoading.visibility = View.VISIBLE
                is Success -> {
                    binding.pbScrapLoading.visibility = View.GONE
                    if (event.data.isEmpty()) {
                        requireActivity().showToast("스크랩한 게시물이 없습니다")
                    }
                    scrapPostList = event.data.map { scrapPost -> scrapPost.toPost() }
                    postAdapter?.submitList(scrapPostList)
                }

                is Failure -> {
                    binding.pbScrapLoading.visibility = View.GONE
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postAdapter = null
        scrapPostList = null
    }
}
