package org.seonhwan.android.veloginmobile.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.seonhwan.android.veloginmobile.BuildConfig.VELOG_GOOGLE_CLIENT_ID
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ActivityLoginBinding
import org.seonhwan.android.veloginmobile.util.binding.BindingActivity
import org.seonhwan.android.veloginmobile.util.extension.showToast

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(VELOG_GOOGLE_CLIENT_ID)
        .requestServerAuthCode(VELOG_GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()

    private val mGoogleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.layoutLoginButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                Log.d("resultLauncher", result.data.toString())
                handleSignInResult(task)
            } else {
                showToast("문제가 발생하였습니다")
                Log.d("result", result.toString())
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
        } catch (e: ApiException) {
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }
//    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
//    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        Log.d("result ㅁㄴㅇㄻㄴㅇㄹ", result.toString())
//
//        try {
//            val account = task.getResult(ApiException::class.java)
//
//            // 이름, 이메일 등이 필요하다면 아래와 같이 account를 통해 각 메소드를 불러올 수 있다.
//            val userName = account.givenName
//            val serverAuth = account.serverAuthCode
//
//            moveSignUpActivity()
//
//        } catch (e: ApiException) {
//            Timber.d(e)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        addListener()
//    }
//
//    private fun addListener() {
//        binding.layoutLoginButton.setOnClickListener {
//            requestGoogleLogin()
//        }
//    }
//
//    private fun requestGoogleLogin() {
//        googleSignInClient.signOut()
//        val signInIntent = googleSignInClient.signInIntent
//        googleAuthLauncher.launch(signInIntent)
//    }
//
//    private fun getGoogleClient(): GoogleSignInClient {
//        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestServerAuthCode(VELOG_GOOGLE_CLIENT_ID) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
//            .requestEmail() // 이메일도 요청할 수 있다.
//            .build()
//
//        return GoogleSignIn.getClient(applicationContext, googleSignInOption)
//    }
//
//    private fun moveSignUpActivity() {
//        this.run {
//            startActivity(Intent(applicationContext, LoginActivity::class.java))
//            finish()
//        }
//    }
}
