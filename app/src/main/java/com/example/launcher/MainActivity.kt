package com.example.launcher

import android.app.NativeActivity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.example.launcher.config.NativeConfig
import net.zhuoweizhang.mcpelauncher.MaraudersMap
import java.io.File

class MainActivity : NativeActivity() {
    private var nativeConfig: NativeConfig? = null
    private lateinit var soDir: File
    private lateinit var so: File

    override fun onCreate(savedInstanceState: Bundle?) {
        // copy so to internal directory
        val mcpeContext = createPackageContext("com.example.native_activity", CONTEXT_IGNORE_SECURITY)
        soDir = getDir("patched", Context.MODE_PRIVATE)
        so = File(soDir, "libnative.so")
        File(mcpeContext.applicationInfo.nativeLibraryDir).copyRecursively(soDir, true)

        nativeConfig = NativeConfig.create(this, soDir)
        with(nativeConfig!!) {
            addLibraryPath()
            setFakeManager(true)
            super.onCreate(savedInstanceState)
            setFakeManager(false)
        }

        initPatching()
        initHooks(so.absolutePath)

        //hook examples
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

    @Throws(Exception::class)
    private fun initPatching() {
        System.loadLibrary("mcpelauncher_tinysubstrate")
        System.loadLibrary("native")
        System.loadLibrary("test")
        if (!MaraudersMap.initPatching(this, so.length())) {
            println("Well, that sucks!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        release()
        System.gc()
    }


    private external fun initHooks(pathname:String)
    private external fun getStringUTF(): String
    private external fun add(a: Int, b: Int): Int
    private external fun release()


}
