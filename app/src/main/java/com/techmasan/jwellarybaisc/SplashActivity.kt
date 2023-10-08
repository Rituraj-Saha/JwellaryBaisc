package com.techmasan.jwellarybaisc

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.techmasan.jwellarybaisc.databinding.ActivitySplashBinding
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.viewModels.ConfigViewModel
import com.techmasan.jwellarybaisc.networkConfig.viewModels.TokenExpireViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


lateinit var binding:ActivitySplashBinding
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    val tokenExpireViewModel: TokenExpireViewModel by viewModels()
    val configViewModel: ConfigViewModel by viewModels()
    var flag = true
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

        lifecycleScope.launch {
            configViewModel.getConfig()
            configViewModel.requestHelpline()
        }
        configViewModel.configResponse.observe(this@SplashActivity){
            when(it){
                is NetworkResult.Loading->{

                }
                is NetworkResult.Failure ->{
                    val builder: AlertDialog.Builder = Builder(this@SplashActivity)

                    // Set the message show for the Alert time

                    builder.setMessage(it.errorMessage)
                    // Set Alert Title
                    builder.setTitle("Alert !")
                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false)
                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Close",
                        DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                            // When the user click yes button then app will close
                            finish()
                        } as DialogInterface.OnClickListener)

                    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                    /*builder.setNegativeButton("No",
                        DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                            // If user click no then dialog box is canceled.
                            dialog.cancel()
                        } as DialogInterface.OnClickListener)*/

//                        // Create the Alert dialog
                    val alertDialog: AlertDialog = builder.create()
                    // Show the Alert Dialog box

                    alertDialog.show()
                }
                is NetworkResult.Success ->{
                    val pInfo: PackageInfo =
                        this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
                    val versionName = pInfo.versionName
                    val versionCode = pInfo.versionCode

                    if(!it.data.ACTIVE.equals("yes")){
                        flag = false
                        val builder: AlertDialog.Builder = Builder(this@SplashActivity)

                        // Set the message show for the Alert time

                        builder.setMessage("App is Under Service")
                        // Set Alert Title
                        builder.setTitle("Alert !")
                        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                        builder.setCancelable(false)
                        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setPositiveButton("Close",
                            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                                // When the user click yes button then app will close
                                finish()
                            } as DialogInterface.OnClickListener)

                        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                        /*builder.setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                                // If user click no then dialog box is canceled.
                                dialog.cancel()
                            } as DialogInterface.OnClickListener)*/

//                        // Create the Alert dialog
                        val alertDialog: AlertDialog = builder.create()
                        // Show the Alert Dialog box

                        alertDialog.show()
                    }
                    if(!it.data.VERSIONCODE.toString().equals(versionCode.toString())){
                        flag = false
                        val builder: AlertDialog.Builder = Builder(this@SplashActivity)

                        // Set the message show for the Alert time

                        builder.setMessage("Please Update App to continue")
                        // Set Alert Title
                        builder.setTitle("Alert !")
                        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                        builder.setCancelable(false)
                        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setPositiveButton("Close",
                            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                                // When the user click yes button then app will close
                                finish()
                            } as DialogInterface.OnClickListener)

                        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                        /*builder.setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                                // If user click no then dialog box is canceled.
                                dialog.cancel()
                            } as DialogInterface.OnClickListener)*/

//                        // Create the Alert dialog
                        val alertDialog: AlertDialog = builder.create()
                        // Show the Alert Dialog box

                        alertDialog.show()

                    }
                    if (!it.data.VERSIONNAME.equals(versionName)){
                        flag = false
                        val builder: AlertDialog.Builder = Builder(this@SplashActivity)

                        // Set the message show for the Alert time

                        builder.setMessage("App is Under Service")
                        // Set Alert Title
                        builder.setTitle("Alert !")
                        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                        builder.setCancelable(false)
                        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setPositiveButton("Close",
                            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                                // When the user click yes button then app will close
                                finish()
                            } as DialogInterface.OnClickListener)

                        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                        /*builder.setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                                // If user click no then dialog box is canceled.
                                dialog.cancel()
                            } as DialogInterface.OnClickListener)*/

//                        // Create the Alert dialog
                        val alertDialog: AlertDialog = builder.create()
                        // Show the Alert Dialog box

                        alertDialog.show()
                    }
                    if(flag){

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
                                }
                                else{
                                    val i = Intent(this@SplashActivity, LoginActivity::class.java)
                                    startActivity(i)
                                    finish()
                                }
                            }
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
                                            configViewModel.helpLineResponse.observe(this@SplashActivity){
                                                when(it){
                                                    is NetworkResult.Loading->{
                                                        Log.d("helpine","loading")
                                                    }
                                                    is NetworkResult.Success->{

                                                        Util.HELPLINE = it.data.helpLine
                                                        Log.d("helpine",Util.HELPLINE+"a")
                                                        val i = Intent(this@SplashActivity, MainActivity::class.java)
                                                        startActivity(i)
                                                        finish()
                                                    }
                                                    is NetworkResult.Failure->{
                                                        Log.d("helpine","failed"+it.errorMessage)
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
                                configViewModel.helpLineResponse.observe(this@SplashActivity){
                                    when(it){
                                        is NetworkResult.Loading->{
                                            Log.d("helpine","loading")
                                        }
                                        is NetworkResult.Success->{

                                            Util.HELPLINE = it.data.helpLine
                                            Log.d("helpine",Util.HELPLINE+"a")
                                            val i = Intent(this@SplashActivity, MainActivity::class.java)
                                            startActivity(i)
                                            finish()
                                        }
                                        is NetworkResult.Failure->{
                                            Log.d("helpine","failed"+it.errorMessage)
                                        }
                                    }
                                }
                                val i = Intent(this@SplashActivity, MainActivity::class.java)
                                startActivity(i)
                                finish()
                            }, 3000)
                        }
                        // on below line we are calling
                        // handler to run a task
                        // for specific time interval
                    }

                }

            }
        }


    }

}