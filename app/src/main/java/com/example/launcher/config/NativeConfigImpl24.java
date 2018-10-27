package com.example.launcher.config;

import android.content.Context;
import dalvik.system.BaseDexClassLoader;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

class NativeConfigImpl24 extends NativeConfigBase {
    NativeConfigImpl24(Context context, File path) {
        super(context, path);
    }

    /**
     * Brief structure of BaseDexClassLoader(android O, 26)
     *
     * <pre>
     * public class dalvik.system.BaseDexClassLoader extends ClassLoader {
     *     private final DexPathList pathList;
     *     public String findLibrary(String name)
     * }
     *
     * final class dalvik.system.DexPathList {
     *     private final NativeLibraryElement[] nativeLibraryPathElements;
     *     public DexPathList(ClassLoader definingContext, String dexPath, String librarySearchPath, File optimizedDirectory)
     *
     *     static class NativeLibraryElement {
     *         public NativeLibraryElement(File dir)
     *         public NativeLibraryElement(File zip, String zipDir)
     *         public String findNativeLibrary(String name)
     *     }
     * }
     * </pre>
     */
    public void addLibraryPath() {
        try {
            Context context = mContext;
            // DexPathList pathList = context.getClassLoader().pathList
            Field field_pathList = BaseDexClassLoader.class.getDeclaredField("pathList");
            field_pathList.setAccessible(true);
            Object pathList = field_pathList.get(context.getClassLoader());

            // NativeLibraryElement[] nativeLibraryPathElements = pathList.nativeLibraryPathElements
            Class<?> class_DexPathList = context.getClassLoader().loadClass("dalvik.system.DexPathList");
            Field field_nativeLibraryPathElements = class_DexPathList.getDeclaredField("nativeLibraryPathElements");
            field_nativeLibraryPathElements.setAccessible(true);
            Object[] nativeLibraryPathElements = (Object[]) field_nativeLibraryPathElements.get(pathList);

            // NativeLibraryElement nativeLibraryElement = new NativeLibraryElement(path)
            Class<?> NativeLibraryElement = context.getClassLoader().loadClass("dalvik.system.DexPathList$NativeLibraryElement");
            Constructor<?> constructor_NativeLibraryElement = NativeLibraryElement.getConstructor(File.class);
            constructor_NativeLibraryElement.setAccessible(true);
            Object nativeLibraryElement = constructor_NativeLibraryElement.newInstance(path);

            // copy nativeLibraryPathElements to a new array whose length=N+1
            int N = nativeLibraryPathElements.length;
            Object[] newNativeLibraryPathElements = (Object[]) Array.newInstance(NativeLibraryElement, N + 1);
            System.arraycopy(nativeLibraryPathElements, 0, newNativeLibraryPathElements, 1, N);
            newNativeLibraryPathElements[0] = nativeLibraryElement;

            // set nativeLibraryPathElements to the new array
            field_nativeLibraryPathElements.set(pathList, newNativeLibraryPathElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
