package com.techmasan.jwellarybaisc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.databinding.ActivitySplashBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.viewModels.LoginViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.TokenExpireViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

lateinit var binding:ActivitySplashBinding
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    val tokenExpireViewModel: TokenExpireViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // on below line we are configuring
        // our window to full screen

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val TAG ="splash"

        if(Util.isLogin(this)){

            // on below line we are
            // starting a new activity.
            Log.d(TAG,"in side login")
            lifecycleScope.launch {
                Log.d(TAG,Util.getToken(this@SplashActivity)!!)
                if(Util.getToken(this@SplashActivity)!=null &&
                    !Util.getToken(this@SplashActivity).equals("")){
                    Log.d(TAG,"in side token")
                    tokenExpireViewModel.chekTokenExpire(Util.getToken(this@SplashActivity)!!.replace("Bearer ",""))
                    tokenExpireViewModel.tokenExpireResponse.observe(this@SplashActivity){
                        when(it){
                            is NetworkResult.Loading -> {
                                Log.d(TAG,"LOADING")
                            }
                            is NetworkResult.Failure -> {
                                Log.d(TAG,it.errorMessage)
                                Util.mToast(this@SplashActivity,it.errorMessage)
                                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                            is  NetworkResult.Success -> {
                                Log.d(TAG,"Success "+it.data)
                                if(it.data){
                                    val i = Intent(this@SplashActivity, MainActivity::class.java)
                                    startActivity(i)
                                    finish()
                                }
                                else{
                                    val i = Intent(this@SplashActivity, LoginActivity::class.java)
                                    startActivity(i)
                                    finish()
                                }
                            }
                        }

                    }
                }
                else{
                    val i = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }
        else{
            Handler().postDelayed({
                // on below line we are
                // creating a new intent
                // on below line we are
                // starting a new activity.

                // on the below line we are finishing
                // our current activity.
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }, 2000)
        }
        // on below line we are calling
        // handler to run a task
        // for specific time interval

    }

}