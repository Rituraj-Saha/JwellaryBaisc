package com.techmasan.jwellarybaisc

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

object Util {
    fun makeDialog(layoutId:Int,activity:Activity):Dialog{
        var dialog = Dialog(activity)
        dialog.setContentView(layoutId);
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.animation;

        return dialog
    }
    var IS_LOGIN:Boolean = false
    fun isLogin():Boolean{
        return IS_LOGIN;
    }
    fun mToast(activity: Activity,msg:String){
        Toast.makeText(activity.applicationContext,msg,Toast.LENGTH_LONG).show()
    }
}