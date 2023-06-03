package org.seonhwan.android.veloginmobile.ui.addtag

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityAddTagBinding
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
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
    private var popularTagAdapter: PopularTagAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel

        initAdapter()
        getTagList()
        initPopularTag()
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

        popularTagAdapter = PopularTagAdapter()

        binding.rvAddtagMyTag.adapter = myTagAdapter
        binding.rvAddtagPopularTag.adapter = popularTagAdapter
    }

    private fun getTagList() {
        viewModel.tagListState.observe(this) { state ->
            when (state) {
                is Success -> {
                    myTagAdapter?.submitList(viewModel.tagList.value)
                }

                is Failure -> {
                    Timber.tag("tagListState").e("Failure")
                }

                is Error -> {
                    Timber.tag("tagListState").e("Error")
                }
            }
        }
    }

    private fun addTag() {
        viewModel.addTagState.observe(this) { state ->
            when (state) {
                is Success -> {
                    viewModel.getTag()
                    setResult(RESULT_OK)
                    showToast("태그를 추가하였습니다")
                }

                is Failure -> {
                    when (state.code) {
                        CODE_400 -> showToast("이미 추가된 태그입니다")
                        else -> showToast("추가할 태그를 입력해주세요")
                    }
                }

                is Error -> {
                    showToast("문제가 발생하였습니다")
                }
            }
        }
    }

    private fun initPopularTag() {
        viewModel.popularTagState.observe(this) { state ->
            when (state) {
                is Success -> {
                    popularTagAdapter?.submitList(viewModel.popularTagList.value)
                }

                is Failure -> {
                    showToast("인기 태그를 불러올 수 없습니다")
                }

                is Error -> {}
            }
        }
    }

    private fun onClickBackButton() {
        binding.ibAddtagBackButton.setOnClickListener {
            if (!isFinishing) finish()
        }
    }
}
