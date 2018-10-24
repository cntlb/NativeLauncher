package com.example.launcher

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import dalvik.system.BaseDexClassLoader

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.launcher", appContext.packageName)
    }

    @Test
    fun findLibrary(){
        val appContext = InstrumentationRegistry.getTargetContext()
        val loader = appContext.classLoader as BaseDexClassLoader
        val findLibrary = loader.findLibrary("native")
        Log.e(javaClass.simpleName, findLibrary)
    }
}
