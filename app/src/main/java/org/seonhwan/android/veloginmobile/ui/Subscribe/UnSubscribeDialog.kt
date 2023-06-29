package org.seonhwan.android.veloginmobile.ui.Subscribe

import android.os.Bundle
import android.view.View
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentDeleteDialogBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingDialog

class UnSubscribeDialog(
    private val unSubscribe: () -> Unit,
) : BindingDialog<FragmentDeleteDialogBinding>(R.layout.fragment_delete_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        onClickCancelButton()
        onClickDeleteButton()
    }

    private fun init() {
        binding.tvDeleteDialogWarningMessage.text = "정말 팔로우를 취소하겠습니까?"
        binding.tvDeleteDialogCancelButton.text = "아니요"
        binding.tvDeleteDialogDeleteButton.text = "네"
    }

    private fun onClickCancelButton() {
        binding.tvDeleteDialogCancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickDeleteButton() {
        binding.tvDeleteDialogDeleteButton.setOnClickListener {
            unSubscribe()
            dismiss()
        }
    }
}
