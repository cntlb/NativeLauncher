package com.example.launcher

import android.app.NativeActivity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.example.launcher.config.NativeConfig
import java.io.File

class MainActivity : NativeActivity() {
    private var nativeConfig: NativeConfig? = null
    private lateinit var soDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        nativeConfig = NativeConfig.create(this)
        // copy so to internal directory
        val mcpeContext = createPackageContext("com.example.native_activity", CONTEXT_IGNORE_SECURITY)
        soDir = getDir("patched", Context.MODE_PRIVATE)
        File(mcpeContext.applicationInfo.nativeLibraryDir).copyRecursively(soDir, true)

        // replace PackageManager
        with(nativeConfig!!) {
            addLibraryPath(soDir)
            setFakeManager(true)
            super.onCreate(savedInstanceState)
            setFakeManager(false)
        }

//        System.loadLibrary("native")
        System.loadLibrary("test")
        Log.e(javaClass.simpleName, getStringUTF())
        Log.e(javaClass.simpleName, "1+2=" + add(1, 2))
    }


    override fun getPackageManager(): PackageManager {
        val pm = super.getPackageManager()
        return when (nativeConfig) {
            null -> pm
            else -> nativeConfig!!.getPackageManager(pm)
        }
    }

    private external fun getStringUTF(): String
    private external fun add(a: Int, b: Int): Int


}
