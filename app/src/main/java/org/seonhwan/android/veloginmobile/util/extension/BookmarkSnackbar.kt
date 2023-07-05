package org.seonhwan.android.veloginmobile.util.extension

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.SnackbarBookmarkBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapBottomSheetFragment
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment

class BookmarkSnackbar(
    private val post: Post,
    private val activity: AppCompatActivity,
    view: View,
    private val message: String,
) {

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", BaseTransientBottomBar.LENGTH_SHORT)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val binding: SnackbarBookmarkBinding = SnackbarBookmarkBinding.inflate(inflater)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            addView(binding.root, 0)
        }
    }

    private fun initData() {
        binding.tvSnackbar.text = message
        binding.ivSnackbarPutFolder.setOnClickListener {
            val bottomSheetFragment = ScrapBottomSheetFragment()
            bottomSheetFragment.arguments = Bundle().apply {
                putParcelable(POST_KEY, post)
            }
            bottomSheetFragment.show(
                activity.supportFragmentManager,
                bottomSheetFragment.tag,
            )
            snackbar.dismiss()
        }
    }

    fun show() {
        snackbar.show()
    }

    companion object {
        fun make(post: Post, activity: AppCompatActivity, view: View, message: String) =
            BookmarkSnackbar(post, activity as AppCompatActivity, view, message)
        const val POST_KEY = "post"
    }
}
