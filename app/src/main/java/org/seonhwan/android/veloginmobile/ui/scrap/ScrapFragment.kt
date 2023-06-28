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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.databinding.FragmentScrapBinding
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class ScrapFragment : BindingFragment<FragmentScrapBinding>(R.layout.fragment_scrap) {
    private val viewModel by viewModels<ScrapViewModel>()
    private var scrapHeaderAdapter: ScrapHeaderAdapter? = null
    private var scrapFolderAdapter: ScrapFolderAdapter? = null
    private var folderList = mutableListOf<Folder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initAllScrapPost()
        initFolder()
    }

    private fun init() {
        viewModel.getAllScrapPost()
        viewModel.getFolder()
    }

    private fun initAdapter() {
        scrapHeaderAdapter = ScrapHeaderAdapter { onClickAddFolder() }

        scrapFolderAdapter = ScrapFolderAdapter { folderName ->
            intentToScrapFolderPost(folderName)
        }

        val gridLayoutManager = GridLayoutManager(context, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        binding.rvScrapFolderList.layoutManager = gridLayoutManager

        binding.rvScrapFolderList.adapter = ConcatAdapter(scrapHeaderAdapter, scrapFolderAdapter)
    }

    private fun initAllScrapPost() {
        viewModel.getAllScrapPostState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    folderList.clear()
                    folderList.add(Folder("모든 스크랩", event.data.size))
                }

                is Failure -> requireActivity().showToast("문제가 발생하였습니다")
            }
        }.launchIn(lifecycleScope)
    }

    private fun initFolder() {
        viewModel.getFolderState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> binding.pbScrapLoading.visibility = View.VISIBLE
                is Success -> {
                    binding.pbScrapLoading.visibility = View.GONE
                    event.data.map { folder ->
                        folderList.add(folder)
                    }
                    initAdapter()
                    scrapFolderAdapter?.submitList(folderList)
                }

                is Failure -> {
                    binding.pbScrapLoading.visibility = View.GONE
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun intentToScrapFolderPost(folderName: String) {
        val intent = Intent(activity, ScrapPostActivity::class.java)
        intent.putExtra(FOLDER_NAME, folderName)
        getResultAddTag.launch(intent)
    }

    private val getResultAddTag = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            init()
        }
    }

    private fun onClickAddFolder() {
        val dialog = AddFolderDialog { folderName -> viewModel.addFolder(folderName) }

        dialog.show(requireFragmentManager(), "AddFolder")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrapHeaderAdapter = null
        scrapFolderAdapter = null
    }

    companion object {
        const val FOLDER_NAME = "folderName"
    }
}
