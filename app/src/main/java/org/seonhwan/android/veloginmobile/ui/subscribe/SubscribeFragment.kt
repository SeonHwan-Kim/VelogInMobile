package org.seonhwan.android.veloginmobile.ui.subscribe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.FragmentSubscribeBinding
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingFragment
import org.seonhwan.android.veloginmobile.util.extension.showToast

@AndroidEntryPoint
class SubscribeFragment : BindingFragment<FragmentSubscribeBinding>(R.layout.fragment_subscribe) {
    private val viewModel by viewModels<SubscribeViewModel>()
    private var subscriberAdapter: SubscriberAdapter? = null
    private var unSubscribeDialog: UnSubscribeDialog? = null
    private var searchUserBottomSheet: SearchUserBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initSubscriber()
        refreshUnsubscribe()
        onClickSearchUserButton()
    }

    private fun initAdapter() {
        subscriberAdapter =
            SubscriberAdapter { subscriberName -> onClickUnSubscribeButton(subscriberName) }

        binding.rvSubscribe.adapter = subscriberAdapter
    }

    private fun initSubscriber() {
        viewModel.subscriberList.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    binding.pbSubscribeLoading.visibility = View.GONE
                    if (event.data.isEmpty()) {
                        binding.ivSubscribeNoSubscriber.visibility = View.VISIBLE
                        binding.rvSubscribe.visibility = View.GONE
                    } else {
                        binding.ivSubscribeNoSubscriber.visibility = View.GONE
                        binding.rvSubscribe.visibility = View.VISIBLE
                        subscriberAdapter?.submitList(event.data)
                    }
                }

                is Failure -> {
                    binding.pbSubscribeLoading.visibility = View.GONE
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun onClickUnSubscribeButton(subscriberName: String) {
        unSubscribeDialog = UnSubscribeDialog { viewModel.unSubscribe(subscriberName) }

        unSubscribeDialog?.show(requireActivity().supportFragmentManager, "unSubscribe")
    }

    private fun refreshUnsubscribe() {
        viewModel.deleteSubscriberState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    viewModel.getSubscriber()
                }

                is Failure -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun onClickSearchUserButton() {
        binding.ibSubscribeToolbarSearch.setOnClickListener {
            searchUserBottomSheet = SearchUserBottomSheet()

            searchUserBottomSheet?.show(
                requireActivity().supportFragmentManager,
                "searchUserBottomSheet",
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriberAdapter = null
        unSubscribeDialog = null
        searchUserBottomSheet = null
    }
}
