package com.techmasan.jwellarybaisc

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


object Util {
    fun makeDialog(layoutId:Int,activity:Activity):Dialog{
        var dialog = Dialog(activity)
        dialog.setContentView(layoutId);
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.animation;

        return dialog
    }
    var IS_LOGIN:Boolean = true
    fun isLogin():Boolean{
        return IS_LOGIN;
    }
    fun mToast(activity: Activity,msg:String){
        Toast.makeText(activity.applicationContext,msg,Toast.LENGTH_LONG).show()
    }

    fun callFragment(fragmentClass: Fragment?,supportFragmentManager: FragmentManager,frameId:Int) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(frameId, fragmentClass!!)
            .addToBackStack("adds").commit()

    }
    fun showProgress(context: Context,title:String,msg:String):ProgressDialog{
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle(title)
        progressDialog.setMessage(msg)
        return progressDialog
    }
}