package com.example.launcher.config;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

class NativeConfigImplBase extends NativeConfigBase {
    private boolean mFakeManager;

    NativeConfigImplBase(Context context, File path) {
        super(context, path);
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

    /**
     * android-20
     * <pre>
     *      public class dalvik.system.BaseDexClassLoader extends ClassLoader {
     *          private final DexPathList pathList;
     *          public String findLibrary(String name)
     *      }
     *
     *      final class dalvik.system.DexPathList {
     *          private final File[] nativeLibraryDirectories;
     *
     *      }
     * </pre>
     */
    @Override
    public void addLibraryPath() {
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
        } catch (Exception ignore) {
        }
    }

    private static File[] addToFileList(File[] files, File toAdd) {
        for (File file : files) {
            if (file.equals(toAdd)) {
                return files;
            }
        }
        File[] retval = new File[files.length + 1];
        System.arraycopy(files, 0, retval, 1, files.length);
        retval[0] = toAdd;
        return retval;
    }

}
