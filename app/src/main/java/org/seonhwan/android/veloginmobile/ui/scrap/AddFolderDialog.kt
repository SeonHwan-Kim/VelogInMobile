package org.seonhwan.android.veloginmobile.ui.scrap

import android.os.Bundle
import android.view.View
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentAddFolderDialogBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingDialog

class AddFolderDialog(
    private val addFolder: (String) -> Unit,
) :
    BindingDialog<FragmentAddFolderDialogBinding>(R.layout.fragment_add_folder_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickCancelButton()
        onClickAddButton()
    }

    private fun onClickCancelButton() {
        binding.tvAddFolderDialogCancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickAddButton() {
        binding.tvAddFolderDialogAddButton.setOnClickListener {
            addFolder(binding.etAddFolderDialog.text.toString())
            dismiss()
        }
    }
}
