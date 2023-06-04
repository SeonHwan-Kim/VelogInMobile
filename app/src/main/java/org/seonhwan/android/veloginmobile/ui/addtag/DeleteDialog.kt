package org.seonhwan.android.veloginmobile.ui.addtag

import android.os.Bundle
import android.view.View
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentDeleteDialogBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingDialog

class DeleteDialog(
    private val tag: String,
    private val deleteTag: OnClickDeleteTag,
) : BindingDialog<FragmentDeleteDialogBinding>(R.layout.fragment_delete_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickCancelButton()
        onClickDeleteButton()
    }

    private fun onClickCancelButton() {
        binding.tvDeleteDialogCancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickDeleteButton() {
        binding.tvDeleteDialogDeleteButton.setOnClickListener {
            deleteTag.deleteTag(tag)
            dismiss()
        }
    }
}
