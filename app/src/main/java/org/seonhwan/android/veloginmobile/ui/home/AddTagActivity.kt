package org.seonhwan.android.veloginmobile.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityAddTagBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity

class AddTagActivity : BindingActivity<ActivityAddTagBinding>(R.layout.activity_add_tag) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)
    }
}
