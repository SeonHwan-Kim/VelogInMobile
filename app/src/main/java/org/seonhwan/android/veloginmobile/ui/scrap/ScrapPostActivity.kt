package org.seonhwan.android.veloginmobile.ui.scrap

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.data.local.model.toPost
import org.seonhwan.android.veloginmobile.databinding.ActivityScrapPostBinding
import org.seonhwan.android.veloginmobile.ui.home.VelogAdapter
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment.Companion.FOLDER_NAME
import org.seonhwan.android.veloginmobile.ui.webview.WebViewActivity
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class ScrapPostActivity : BindingActivity<ActivityScrapPostBinding>(R.layout.activity_scrap_post) {
    private val viewModel by viewModels<ScrapViewModel>()
    private var folderName: String? = null
    private var scrapFolderPostAdapter: VelogAdapter? = null
    private var scrapFolderHeaderAdapter: ScrapFolderHeaderAdapter? = null
    private var scrapDeleteFolderBottomSheet: ScrapDeleteFolderBottomSheetFragment? = null
    private var scrapPostList: List<ScrapPost>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTitleName()
        initScrapPost()
        getScrapPost()
        onClickBackButton()
    }

    private fun getTitleName() {
        folderName = intent.getStringExtra(FOLDER_NAME)
        binding.tvScrapPostHeader.text = folderName
    }

    private fun initScrapPost() {
        when (folderName) {
            "모든 스크랩" -> viewModel.getAllScrapPost()
            else -> {
                viewModel.getFolderScrapPost(folderName!!)
            }
        }
    }

    private fun initAdapter() {
        scrapFolderHeaderAdapter = ScrapFolderHeaderAdapter(
            { onClickDeleteFolder() },
            { onClickChangeFolderName() },
        )

        scrapFolderPostAdapter = VelogAdapter(
            this,
            { post ->
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(VelogAdapter.VELOG, post)
                getResultSubscribe.launch(intent)
            },
            { post ->
                viewModel.scrapPost(post, null)
            },
            { post ->
                viewModel.deleteScrapPost(post, getScrapPostFolder(post.url))
            },
            scrapPostList?.map { scrapPost -> scrapPost.toPost() },
        )

        if (folderName == "모든 스크랩") {
            binding.rvHomePost.adapter = scrapFolderPostAdapter
        } else {
            binding.rvHomePost.adapter =
                ConcatAdapter(scrapFolderHeaderAdapter, scrapFolderPostAdapter)
        }
    }

    private val getResultSubscribe = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            initScrapPost()
        }
    }

    private fun getScrapPost() {
        viewModel.getAllScrapPostState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> binding.pbHomeLoading.visibility = View.VISIBLE

                is Success -> {
                    binding.pbHomeLoading.visibility = View.GONE
                    scrapPostList = event.data
                    initAdapter()
                    scrapFolderPostAdapter?.submitList(scrapPostList?.map { scrapPost -> scrapPost.toPost() })
                }

                is Failure -> {
                    binding.pbHomeLoading.visibility = View.GONE
                    showToast("문제가 발생하였습니다")
                }
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

    private fun onClickBackButton() {
        binding.ivScrapPostBackButton.setOnClickListener {
            if (!isFinishing) finish()
        }
    }

    private fun onClickChangeFolderName() {
    }

    private fun onClickDeleteFolder() {
        scrapDeleteFolderBottomSheet = ScrapDeleteFolderBottomSheetFragment(
            folderName!!,
        ) { closeScrapPostActivity() }

        scrapDeleteFolderBottomSheet?.show(supportFragmentManager, "deleteFolder")
    }

    private fun closeScrapPostActivity() {
        if (!isFinishing) finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        scrapFolderHeaderAdapter = null
        scrapFolderPostAdapter = null
        scrapPostList = null
        scrapDeleteFolderBottomSheet = null
    }
}
