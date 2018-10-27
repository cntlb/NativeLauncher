package com.example.launcher.config;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;

public interface NativeConfig {

    default void setFakeManager(boolean fakeManager) {

    }

    default PackageManager getPackageManager(PackageManager pm) {
        return pm;
    }

    void addLibraryPath();

    static NativeConfig create(Context context, File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new NativeConfigImpl24(context, path);
        }
        return new NativeConfigImplBase(context, path);
    }

    static NativeConfig create(Context context, String path) {
        return create(context, new File(path));
    }

}
