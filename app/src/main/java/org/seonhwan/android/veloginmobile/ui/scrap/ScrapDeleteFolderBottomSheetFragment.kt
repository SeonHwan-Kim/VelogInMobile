package org.seonhwan.android.veloginmobile.ui.scrap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentDeleteFolderBottomSheetBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingBottomSheet

@AndroidEntryPoint
class ScrapDeleteFolderBottomSheetFragment(
    private val folderName: String,
    private val closeScrapPostActivity: () -> Unit,
) : BindingBottomSheet<FragmentDeleteFolderBottomSheetBinding>(
    R.layout.fragment_delete_folder_bottom_sheet,
) {
    private val viewModel by viewModels<ScrapDeleteFolderBottomSheetViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickCloseButton()
        onClickDeleteButton()
    }

    private fun onClickCloseButton() {
        binding.ivDeleteFolderBottomSheetClose.setOnClickListener {
            dismiss()
        }

        binding.tvDeleteFolderBottomSheetCancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickDeleteButton() {
        binding.tvDeleteFolderBottomSheetDeleteButton.setOnClickListener {
            viewModel.deleteScrapFolder(folderName)
            viewModel.deleteFolder(folderName)
            if (requireActivity().isFinishing) requireActivity().finish()
            closeScrapPostActivity()
            dismiss()
        }
    }
}
