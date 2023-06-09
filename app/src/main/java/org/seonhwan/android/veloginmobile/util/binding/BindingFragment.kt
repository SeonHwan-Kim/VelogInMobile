package org.seonhwan.android.veloginmobile.util.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.seonhwan.android.veloginmobile.R

abstract class BindingFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
) : Fragment() {
    private var _binding: T? = null
    protected val binding get() = requireNotNull(_binding) { getString(R.string.binding_error_msg) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
