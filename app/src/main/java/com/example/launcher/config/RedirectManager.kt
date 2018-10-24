package com.example.launcher.config

import android.content.ComponentName
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import java.io.File

class RedirectManager(wrapper: PackageManager, val sodir: String) : PackageManagerWrapper(wrapper) {
    constructor(wrapper: PackageManager, sodir: File) : this(wrapper, sodir.absolutePath)

    override fun getActivityInfo(component: ComponentName?, flags: Int): ActivityInfo {
        val activityInfo = super.getActivityInfo(component, flags)
        activityInfo.applicationInfo.nativeLibraryDir = sodir
        return activityInfo
    }
}
