package org.seonhwan.android.veloginmobile.util.extension

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.SnackbarBookmarkBinding

class BookmarkSnackbar(view: View, private val message: String) {

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
            // ~~
        }
        binding.ivSnackbarGoFolder.setOnClickListener {
            // ~~
        }
    }

    fun show() {
        snackbar.show()
    }

    companion object {
        fun make(view: View, message: String) = BookmarkSnackbar(view, message)
    }
}
