package org.seonhwan.android.veloginmobile.ui.addtag

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityAddTagBinding
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.showToast
import timber.log.Timber

@AndroidEntryPoint
class AddTagActivity : BindingActivity<ActivityAddTagBinding>(R.layout.activity_add_tag) {
    private val viewModel by viewModels<AddTagViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel

        getTagList()
        addTag()
    }

    private fun getTagList() {
        viewModel.tagListState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    viewModel.tagList.value?.map { tag ->
                        with(binding) {
                            Timber.tag("getTagList").d(tag)
                        }
                    }
                }

                is UiState.Failure -> {
                    Timber.tag("tagListState").e("Failure")
                }

                is UiState.Error -> {
                    Timber.tag("tagListState").e("Error")
                }
            }
        }
    }

    private fun addTag() {
        binding.ibAddtagSearch.setOnClickListener {
            viewModel.addTag()
            viewModel.addTagState.observe(this) { state ->
                when (state) {
                    is UiState.Success -> {
                        getTagList()
                        showToast("태그를 추가하였습니다.")
                    }

                    is UiState.Failure -> {
                        showToast("실패")
                        Timber.tag("addTagState").e("Failure")
                    }

                    is UiState.Error -> {
                        Timber.tag("addTagState").e("Error")
                    }
                }
            }
        }
    }
}
