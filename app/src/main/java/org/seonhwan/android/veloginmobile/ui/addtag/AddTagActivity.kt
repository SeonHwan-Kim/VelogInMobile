package org.seonhwan.android.veloginmobile.ui.addtag

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityAddTagBinding
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
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
                showDeleteDialog(tag)
                viewModel.deleteTagState.flowWithLifecycle(lifecycle).onEach { event ->
                    when (event) {
                        is Loading -> {}
                        is Success -> {
                            setResult(RESULT_OK)
                            viewModel.getTag()
                            showToast("$tag 태그가 삭제되었습니다")
                        }

                        is Failure -> {
                            when (event.code) {
                                CODE_400 -> showToast("없는 태그입니다")
                                else -> showToast("문제가 발생하였습니다")
                            }
                        }
                    }
                }.launchIn(lifecycleScope)
            }
        })

        popularTagAdapter = PopularTagAdapter()

        binding.rvAddtagMyTag.adapter = myTagAdapter
        binding.rvAddtagPopularTag.adapter = popularTagAdapter
    }

    private fun showDeleteDialog(tag: String) {
        val dialog = DeleteDialog(
            tag,
            object : OnClickDeleteTag {
                override fun deleteTag(tag: String) {
                    viewModel.deleteTag(tag)
                }
            },
        )
        dialog.show(supportFragmentManager, "DeleteDialog")
    }

    private fun getTagList() {
        viewModel.tagState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> myTagAdapter?.submitList(event.data)
                is Failure -> Timber.d("Failure")
            }
        }.launchIn(lifecycleScope)
    }

    private fun addTag() {
        viewModel.addTagState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> {
                    viewModel.getTag()
                    setResult(RESULT_OK)
                    showToast("태그를 추가하였습니다")
                }

                is Failure -> {
                    when (event.code) {
                        CODE_400 -> showToast("이미 추가된 태그입니다")
                        else -> showToast("추가할 태그를 입력해주세요")
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initPopularTag() {
        viewModel.popularTagState.flowWithLifecycle(lifecycle).onEach { event ->
            when (event) {
                is Loading -> {}
                is Success -> popularTagAdapter?.submitList(event.data)
                is Failure -> showToast("인기 태그를 불러올 수 없습니다")
            }
        }
    }

    private fun onClickBackButton() {
        binding.ibAddtagBackButton.setOnClickListener {
            if (!isFinishing) finish()
        }
    }
}
