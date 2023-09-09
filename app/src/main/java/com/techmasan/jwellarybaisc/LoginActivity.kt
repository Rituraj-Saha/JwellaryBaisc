package com.techmasan.jwellarybaisc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.techmasan.jwellarybaisc.databinding.ActivityLoginBinding
import com.techmasan.jwellarybaisc.fragments.LoginFragment
import com.techmasan.jwellarybaisc.fragments.OtpFragment
import com.techmasan.jwellarybaisc.fragments.SignUpFragment

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
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
    fun switchToOtpFragment(){
        Util.callFragmentWithoutStackTrace(OtpFragment(),supportFragmentManager,binding.frmLayout.id)
    }
    fun switchToMaiinActivity(){
        var intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}