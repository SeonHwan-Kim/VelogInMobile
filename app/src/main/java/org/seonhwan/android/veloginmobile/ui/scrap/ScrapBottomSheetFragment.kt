package org.seonhwan.android.veloginmobile.ui.scrap

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.databinding.FragmentScrapBottomSheetBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.entity.toScrapPost
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingBottomSheet
import org.seonhwan.android.veloginmobile.util.extension.showToast
import timber.log.Timber

@AndroidEntryPoint
class ScrapBottomSheetFragment :
    BindingBottomSheet<FragmentScrapBottomSheetBinding>(R.layout.fragment_scrap_bottom_sheet) {
    private val viewModel by viewModels<ScrapBottomSheetViewModel>()
    private var folderAdapter: FolderAdapter? = null
    private var post: Post? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        post = arguments?.getParcelable("post")

        initAdapter()
        getAllFolderList()
        onClickAddFolder()
        onClickCloseButton()
    }

    private fun initAdapter() {
        folderAdapter = FolderAdapter(
            this,
            { folderName ->
                addScrapPostFolder(folderName)
            },
            { folder ->
                addFolder(folder)
            },
        )

        binding.rvBottomSheetFolder.adapter = folderAdapter
    }

    private fun addScrapPostFolder(folderName: String) {
        post?.toScrapPost(folderName)?.let { viewModel.addScrapPost(it) }
    }

    private fun addFolder(folder: Folder) {
        viewModel.addFolder(folder)
    }

    private fun getAllFolderList() {
        viewModel.getAllFolderList.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {
                    Timber.d("answp")
                }

                is Success -> {
                    folderAdapter?.submitList(event.data)
                    Log.d("getAllFolderList", event.data.toString())
                }

                is Failure -> {
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun onClickAddFolder() {
        with(binding) {
            layoutBottomSheetNewFolder.setOnClickListener {
                layoutBottomSheetNewFolder.visibility = View.INVISIBLE
                layoutBottomSheetEdit.visibility = View.VISIBLE
            }
        }
    }

    private fun onClickCloseButton() {
        binding.ibBottomSheetClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        folderAdapter = null
        post = null
    }
}
