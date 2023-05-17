package org.seonhwan.android.veloginmobile.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import org.seonhwan.android.veloginmobile.MainActivity
import org.seonhwan.android.veloginmobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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