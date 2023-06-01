package org.seonhwan.android.veloginmobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import org.seonhwan.android.veloginmobile.ui.MainActivity
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityLoginBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.onClickLoginButton()
        this.onClickPasswordVisibleButton()
    }

    private fun onClickLoginButton() {
        with(binding) {
            btLoginLogin.setOnClickListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                if (!isFinishing) finish()
            }
        }
    }

    private fun onClickPasswordVisibleButton() {
        with(binding) {
            ivLoginPasswordVisible.setOnClickListener {
                when (etLoginPassword.transformationMethod) {
                    HideReturnsTransformationMethod.getInstance() -> {
                        etLoginPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    }

                    else -> {
                        etLoginPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                    }
                }
            }
        }
    }
}
