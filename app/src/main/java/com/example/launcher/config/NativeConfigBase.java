package com.example.launcher.config;

import android.content.Context;

abstract class NativeConfigBase implements NativeConfig{
    protected Context mContext;

    public NativeConfigBase(Context context) {
        this.mContext = context;
    }
}
