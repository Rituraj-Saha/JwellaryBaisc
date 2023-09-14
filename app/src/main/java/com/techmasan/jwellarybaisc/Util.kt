package com.techmasan.jwellarybaisc

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.techmasan.jwellarybaisc.networkConfig.data.User


object Util {

    fun makeDialog(layoutId:Int,activity:Activity):Dialog{
        var dialog = Dialog(activity)
        dialog.setContentView(layoutId);
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.animation;

        return dialog
    }

    fun mToast(activity: Activity,msg:String){
        Toast.makeText(activity.applicationContext,msg,Toast.LENGTH_LONG).show()
    }

    fun callFragmentWithoutStackTrace(fragmentClass: Fragment?, supportFragmentManager: FragmentManager, frameId:Int) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(frameId, fragmentClass!!)
            .commit()

    }
    fun showProgress(context: Context,title:String,msg:String):ProgressDialog{
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle(title)
        progressDialog.setMessage(msg)
        return progressDialog
    }
    fun phoneInputTextSizeCheck(phoneNumberTextInput:TextInputEditText,phoneNumberTextInputLayout: TextInputLayout,activity: Activity):Boolean{
        if(phoneNumberTextInput.text.toString().length==10)
            return true
        else
        {
            phoneNumberTextInputLayout.setError("Please Enter phone with 10 digit")
            mToast(activity,"Please Enter phone with 10 digit")
            phoneNumberTextInput.setFocusable(true)
            phoneNumberTextInput.requestFocus()
            return false
        }
    }
    fun signUpValidator(nameTextInput:TextInputEditText,
                        emailTextInput:TextInputEditText,
                        phoneNumberTextInput:TextInputEditText,
                        phoneNumberTextInputLayout: TextInputLayout,
                        addressLineTextInputEditText: TextInputEditText,
                        pinCodeTextInputEditText: TextInputEditText,
                        stateTextInputEditText: TextInputEditText,activity: Activity):Boolean{
        if(nameTextInput.text.toString().isEmpty()){
            nameTextInput.setError("Please Enter Name",activity.getDrawable(R.drawable.baseline_error_24))
            nameTextInput.setFocusable(true)
            nameTextInput.requestFocus()
            return false
        }
        if(emailTextInput.text.toString().isEmpty()){
            emailTextInput.setError("Please Enter email",activity.getDrawable(R.drawable.baseline_error_24))
            emailTextInput.setFocusable(true)
            emailTextInput.requestFocus()
            return false
        }
        if(phoneNumberTextInput.text.toString().isEmpty()){
            phoneNumberTextInput.setError("Please Enter phone",activity.getDrawable(R.drawable.baseline_error_24))
            phoneNumberTextInput.setFocusable(true)
            phoneNumberTextInput.requestFocus()
            return false
        }
        if(!phoneNumberTextInput.text.toString().isEmpty()){
            return phoneInputTextSizeCheck(phoneNumberTextInput,phoneNumberTextInputLayout,activity)
        }
        if(addressLineTextInputEditText.text.toString().isEmpty()){
            addressLineTextInputEditText.setError("Please Enter Address line",activity.getDrawable(R.drawable.baseline_error_24))
            addressLineTextInputEditText.setFocusable(true)
            addressLineTextInputEditText.requestFocus()
            return false
        }
        if(pinCodeTextInputEditText.text.toString().isEmpty()){
            pinCodeTextInputEditText.setError("Please Enter Pincode",activity.getDrawable(R.drawable.baseline_error_24))
            pinCodeTextInputEditText.setFocusable(true)
            pinCodeTextInputEditText.requestFocus()
            return false
        }
        if(stateTextInputEditText.text.toString().isEmpty()){
            stateTextInputEditText.setError("Please Enter State",activity.getDrawable(R.drawable.baseline_error_24))
            stateTextInputEditText.setFocusable(true)
            stateTextInputEditText.requestFocus()
            return false
        }
        return true

    }
    fun getSharedPref(activity: Activity):Editor{
        var sharedpreferences: SharedPreferences = activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        return sharedpreferences.edit()
    }


    fun sessionManager(activity: Activity,status:Boolean,token:String,user: User){
       var editor = getSharedPref(activity).putBoolean("isLogin",status)
        editor.putString("token",token)
        val gson = Gson()
        val json = gson.toJson(user)
        editor.putString("user", json)
        editor.commit()
    }
    fun isLogin(activity: Activity):Boolean{
        var sharedpreferences: SharedPreferences = activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean("isLogin",false);
    }

    fun getToken(activity: Activity): String? {
        var sharedpreferences: SharedPreferences = activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        return sharedpreferences.getString("token","");
    }
    fun getUser(activity: Activity):User{
        var sharedpreferences: SharedPreferences = activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedpreferences.getString("user", "")

        var user:User = gson.fromJson<User>(json!!,User::class.java)
        return user
    }
    fun logOut(activity: Activity){
        var editor = getSharedPref(activity)
        editor.remove("token")
        editor.remove("user")
        editor.putBoolean("isLogin",false);
    }



}