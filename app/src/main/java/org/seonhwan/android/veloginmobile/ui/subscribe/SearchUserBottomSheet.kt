package org.seonhwan.android.veloginmobile.ui.subscribe

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentSearchUserBottomSheetBinding
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingBottomSheet
import timber.log.Timber

@AndroidEntryPoint
class SearchUserBottomSheet(
    private val addSubscriberState: (String) -> Unit,
) : BindingBottomSheet<FragmentSearchUserBottomSheetBinding>(R.layout.fragment_search_user_bottom_sheet) {
    private val viewModel by viewModels<SearchUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initSearchButton()
        searchResponse()
    }

    private fun initSearchButton() {
        viewModel.isUserNameNotEmpty.observe(this) { isValid ->
            if (isValid) {
                binding.tvSearchUserSearchButton.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white,
                    ),
                )
                onClickSearchButton()
            } else {
                binding.tvSearchUserSearchButton.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_300,
                    ),
                )
            }
        }
    }

    private fun onClickSearchButton() {
        binding.tvSearchUserSearchButton.setOnClickListener {
            viewModel.searchUser()
        }
    }

    private fun searchResponse() {
        with(binding) {
            viewModel.searchUserState.flowWithLifecycle(lifecycle).onEach { event ->
                when (event) {
                    is Loading -> {}
                    is Failure -> {
                        Timber.d("존재하지 않아요")
                        tvSearchUserNoUser.visibility = View.VISIBLE
                        layoutSearchUserUserInformation.visibility = View.INVISIBLE
                    }

                    is Success -> {
                        tvSearchUserName.text = event.data.userName
                        tvSearchUserNoUser.visibility = View.INVISIBLE

                        if (event.data.profilePictureUrl != "") {
                            ivSearchUserUserProfile.load(event.data.profilePictureUrl) {
                                transformations(CircleCropTransformation())
                            }
                        }

                        addSubscriber(event.data.userName)
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun addSubscriber(userName: String) {
        binding.layoutSearchUserUserInformation.setOnClickListener {
            addSubscriberState(userName)
            dismiss()
        }
    }
}
