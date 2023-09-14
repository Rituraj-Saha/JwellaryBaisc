package com.techmasan.jwellarybaisc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.databinding.ActivityLoginBinding
import com.techmasan.jwellarybaisc.fragments.LoginFragment
import com.techmasan.jwellarybaisc.fragments.OtpFragment
import com.techmasan.jwellarybaisc.fragments.SignUpFragment
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        Util.callFragmentWithoutStackTrace(LoginFragment(),supportFragmentManager,binding.frmLayout.id)
        binding.btnLogin.setOnClickListener {
            binding.btnLogin.background = this.getDrawable(R.drawable.border_02)
            binding.btnSignUp.setBackgroundResource(0)
            Util.callFragmentWithoutStackTrace(LoginFragment(), supportFragmentManager,binding.frmLayout.id)
        }
        binding.btnSignUp.setOnClickListener {
            binding.btnSignUp.background = this.getDrawable(R.drawable.border_02)
            binding.btnLogin.setBackgroundResource(0)
            Util.callFragmentWithoutStackTrace(SignUpFragment(),supportFragmentManager,binding.frmLayout.id)
        }
    }
    fun switchToOtpFragment(phoneNumber:String){
        val otpFragment = OtpFragment()
        var bundle =Bundle();
        bundle.putString("phoneNumber",phoneNumber)
        otpFragment.arguments =bundle
        Util.callFragmentWithoutStackTrace(otpFragment,supportFragmentManager,binding.frmLayout.id)
    }
    fun switchToMaiinActivity(){
        var intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun requestOTP(phoneNumber: String){
        lifecycleScope.launch {
            var uPhone = phoneNumber
            loginViewModel.sendotpRequest(OtpRequest(uPhone));
        }
        loginViewModel.otpResponse.observe(this){
            when(it){
                is NetworkResult.Loading -> {
//                    binding.progressbar.isVisible = it.isLoading
//                    Logger.log("userNetwork","in loading..")
                    Log.d("otp: ","loading")
                }

                is NetworkResult.Failure -> {
                    Log.e("otp: ",it.errorMessage)
                    Util.mToast(this,"No Active User Found Please Consider sign up")
                }
                is  NetworkResult.Success -> {
                    Log.d("otp: ",it.data.otp)
                    Util.mToast(this,"OTP sent Successfully")
                }
            }
        }
    }
}