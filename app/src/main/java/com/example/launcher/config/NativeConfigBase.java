package com.example.launcher.config;

import android.content.Context;

import java.io.File;

abstract class NativeConfigBase implements NativeConfig{
    protected Context mContext;
    protected File path;

    public NativeConfigBase(Context context, File path) {
        this.mContext = context;
        this.path = path;
    }
}
