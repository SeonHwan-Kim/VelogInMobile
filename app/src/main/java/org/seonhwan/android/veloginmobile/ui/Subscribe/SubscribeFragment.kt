package org.seonhwan.android.veloginmobile.ui.Subscribe

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initSubscriber()
    }

    private fun initAdapter() {
        subscriberAdapter = SubscriberAdapter()

        binding.rvSubscribe.adapter = subscriberAdapter
    }

    private fun initSubscriber() {
        viewModel.subscriberList.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    subscriberAdapter?.submitList(event.data)
                }

                is Failure -> {
                    requireActivity().showToast("문제가 발생하였습니다")
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriberAdapter = null
    }
}
