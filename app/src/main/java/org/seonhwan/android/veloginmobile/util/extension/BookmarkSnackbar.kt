package org.seonhwan.android.veloginmobile.util.extension

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.SnackbarBookmarkBinding
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapBottomSheetFragment
import org.seonhwan.android.veloginmobile.ui.scrap.ScrapFragment

class BookmarkSnackbar(
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
            bottomSheetFragment.show(
                activity.supportFragmentManager,
                bottomSheetFragment.tag,
            )
            snackbar.dismiss()
        }
        binding.ivSnackbarGoFolder.setOnClickListener {
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_main, ScrapFragment())
                .commit()
            snackbar.dismiss()
        }
    }

    fun show() {
        snackbar.show()
    }

    companion object {
        fun make(activity: AppCompatActivity, view: View, message: String) =
            BookmarkSnackbar(activity as AppCompatActivity, view, message)
    }
}
