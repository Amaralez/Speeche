package com.example.speeche

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
const val PERMISSION_REQUEST = 200

//fun checkPermission(permission: String):Boolean {
    //return if (Build.VERSION.SDK_INT >23
    //    && ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
    //    ActivityCompat.requestPermissions(this, arrayOf(permission),PERMISSION_REQUEST)
    //        false
   // }else true
//}