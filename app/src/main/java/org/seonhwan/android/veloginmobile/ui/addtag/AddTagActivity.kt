package org.seonhwan.android.veloginmobile.ui.addtag

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityAddTagBinding
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Error
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.showToast
import timber.log.Timber

@AndroidEntryPoint
class AddTagActivity : BindingActivity<ActivityAddTagBinding>(R.layout.activity_add_tag) {
    private val viewModel by viewModels<AddTagViewModel>()
    private var myTagAdapter: MyTagAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel

        initAdapter()
        getTagList()
        addTag()
        onClickBackButton()
    }

    private fun initAdapter() {
        myTagAdapter = MyTagAdapter(object : OnClickDeleteTag {
            override fun deleteTag(tag: String) {
                viewModel.deleteTag(tag)
                viewModel.deleteTagState.observe(this@AddTagActivity) { state ->
                    when (state) {
                        is Success -> {
                            setResult(RESULT_OK)
                            viewModel.getTag()
                            showToast("$tag 태그가 삭제되었습니다")
                        }

                        is Failure -> {
                            when (state.code) {
                                CODE_400 -> showToast("없는 태그입니다")
                                else -> showToast("문제가 발생하였습니다")
                            }
                        }

                        is Error -> {}
                    }
                }
            }
        })

        binding.rvAddtagMyTag.adapter = myTagAdapter
    }

    private fun getTagList() {
        viewModel.tagListState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    myTagAdapter?.submitList(viewModel.tagList.value)
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
        viewModel.addTagState.observe(this) { state ->
            when (state) {
                is AddTagUiState.Success -> {
                    setResult(RESULT_OK)
                    viewModel.getTag()
                    showToast("태그를 추가하였습니다")
                }

                is AddTagUiState.Empty -> {
                    showToast("태그를 입력해주세요")
                }

                is AddTagUiState.Failure -> {
                    showToast("이미 추가된 태그입니다")
                }

                is AddTagUiState.Error -> {
                    showToast("문제가 발생하였습니다")
                }
            }
        }
    }

    private fun onClickBackButton() {
        binding.ibAddtagBackButton.setOnClickListener {
            if (!isFinishing) finish()
        }
    }
}
