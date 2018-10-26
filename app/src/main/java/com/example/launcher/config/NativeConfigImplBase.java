package com.example.launcher.config;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
        try {
            ClassLoader classLoader = mContext.getClassLoader();
            Field field = Utils.getDeclaredFieldRecursive(classLoader.getClass(), "pathList");
            field.setAccessible(true);
            Object pathListObj = field.get(classLoader);
            Field natfield = Utils.getDeclaredFieldRecursive(pathListObj.getClass(), "nativeLibraryDirectories");
            natfield.setAccessible(true);
            Object nativeLibraryDirectories = natfield.get(pathListObj);
            if (nativeLibraryDirectories instanceof ArrayList) {
                ((ArrayList) nativeLibraryDirectories).add(0, path);
            } else if (nativeLibraryDirectories instanceof File[]) {
                File[] fileList = (File[]) nativeLibraryDirectories;
                File[] newList = addToFileList(fileList, path);
                if (fileList != newList) {
                    natfield.set(pathListObj, newList);
                }
            }

        } catch (Exception e) {

        }
    }

    private File[] addToFileList(File[] files, File toAdd) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].equals(toAdd)) {
                return files;
            }
        }
        File[] retval = new File[files.length + 1];
        System.arraycopy(files, 0, retval, 1, files.length);
        retval[0] = toAdd;
        return retval;
    }

}
