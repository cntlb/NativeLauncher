package com.example.launcher.config;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;

class NativeConfigImplBase extends NativeConfigBase {
    private boolean mFakeManager;
    private File path;

    NativeConfigImplBase(Context context) {
        super(context);
    }

    @Override
    public void setFakeManager(boolean fakeManager) {
        mFakeManager = fakeManager;
    }

    @Override
    public PackageManager getPackageManager(PackageManager pm) {
        if (mFakeManager) return new RedirectManager(pm, path);
        return pm;
    }

    @Override
    public void addLibraryPath(File path) {
        this.path = path;
    }
}
