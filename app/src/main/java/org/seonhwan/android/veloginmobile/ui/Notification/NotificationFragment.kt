package org.seonhwan.android.veloginmobile.ui.Notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.seonhwan.android.veloginmobile.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding: FragmentNotificationBinding
        get() = requireNotNull(_binding) { "_binding is not null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}