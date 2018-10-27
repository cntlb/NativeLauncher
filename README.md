单机游戏破解启动器原理
===========

启动器可以启动游戏并允许在没有root设备情况下对游戏进行hook, 这里游戏模拟MinecraftPE
([google native-activity](https://github.com/googlesamples/android-ndk)稍作修改).
项目杂糅了java和kotlin希望见谅.


# NativeActivity
[NativeActivity](https://developer.android.google.cn/reference/android/app/NativeActivity)
是使用纯本地语言(C/C++, 统称C)编写实现的Activity, 因此(类)游戏应用通常使用NativeActivity与C语言编写的
游戏能更好的对接和移植. 游戏代码用C实现,打成动态库(.so)由NativeActivity启动是进行动态链接.

```xml
    <!-- Our activity is the built-in NativeActivity framework class.
         This will take care of integrating with our NDK code. -->
    <activity android:name="android.app.NativeActivity"
              android:label="@string/app_name"
              android:configChanges="orientation|keyboardHidden">
      <!-- Tell NativeActivity the name of our .so -->
      <meta-data android:name="android.app.lib_name"
                 android:value="native-activity" />
    </activity>
```
当应用启动一个NativeActivity(或子类)时, `android.app.lib_name`在`NativeActivity.onCreate`中被解析出来,
由版本而查找所需的动态库有所不同, 这部分代码实现基于[NativeConfig](app/src/main/java/com/example/launcher/config/NativeConfig.java),
参考不同版本NativeActivity, BaseDexClassLoader, DexPathList等源码理解.

# System.loadLibrary搜索路径
在没有root权限下对于任何应用的下lib目录均没有修改的权限, 一般只有读权限. 要启动游戏必须配合lib下的so,正确的加载到
才能将游戏启动, 因此需要设置正确的so搜索路径.

`System.loadLibrary`与`System.load`的差别在于前者只需要libname即可在已有的路径用搜索, 而后者需要传递一个绝对路径.
为了能更好的配合NativeActivity工作,这里将游戏的lib目录拷贝到NativeLauncher/app_patched下
```kotlin
    val mcpeContext = createPackageContext("com.example.native_activity", CONTEXT_IGNORE_SECURITY)
    soDir = getDir("patched", Context.MODE_PRIVATE)
    so = File(soDir, "libnative.so")
    File(mcpeContext.applicationInfo.nativeLibraryDir).copyRecursively(soDir, true)
```

# Hook游戏
使用[Tiny Substrate](https://drive.google.com/file/d/0B50ApLKOyu8bX3VnRmYzNUcxT0U/edit?usp=sharing)
由于少有文档说明,建议参考[cydiasubstrate](http://www.cydiasubstrate.com/inject/). cydiasubstrate做hook需要
root的设备, 项目中通过重新映射动态库内存, 修改其读写权限, 使得Substrate API可以正常使用. 以下实现代码来自于
[zhuowei/MCPELauncher](https://github.com/zhuowei/MCPELauncher)稍作修改.

[MaraudersMap.java](https://github.com/zhuowei/MCPELauncher/blob/master/src/net/zhuoweizhang/mcpelauncher/MaraudersMap.java)
[marauders_map.c](https://github.com/zhuowei/MCPELauncher/blob/master/jni/marauders_map.c)
[PokerFace.java](https://github.com/zhuowei/MCPELauncher/blob/master/src/net/zhuoweizhang/pokerface/PokerFace.java)
[nativepatch.c](https://github.com/zhuowei/MCPELauncher/blob/master/jni/nativepatch.c)

Hook失败:`signal 11 (SIGSEGV), code 2 (SEGV_ACCERR)`

# Android6.0变更
> https://developer.android.google.cn/about/versions/marshmallow/android-6.0-changes#behavior-runtime
>
> 此版本更新了动态链接程序的行为。动态链接程序现在可以识别库的 soname 与其路径之间的差异（公开错误 6670），
> 并且现在已实现了按 soname 搜索。之前包含错误的 DT_NEEDED 条目（通常是开发计算机文件系统上的绝对路径）
> 却仍工作正常的应用，如今可能会出现加载失败。

该变更直接导致了不含有`SONAME`段的so作为其他so的`DT_NEEDED`时加载失败:
    启动器libtest.so依赖于游戏的libnative.so
```shell
$ readelf -d libtest.so|grep NEEDED
 0x00000001 (NEEDED)                     Shared library: [libmcpelauncher_tinysubstrate.so]
 0x00000001 (NEEDED)                     Shared library: [libnative.so]
 0x00000001 (NEEDED)                     Shared library: [liblog.so]
 0x00000001 (NEEDED)                     Shared library: [libstdc++.so]
 0x00000001 (NEEDED)                     Shared library: [libc.so]
 0x00000001 (NEEDED)                     Shared library: [libm.so]
 0x00000001 (NEEDED)                     Shared library: [libdl.so]

$ readelf -d libnative.so|grep SONAME
 0x0000000e (SONAME)                     Library soname: [libnative.so]

```

* android6.0以前只要能搜索libnative.so就可以正确加载libtest.so
* android6.0开始, 如果在libtest.so中查询不到`SONAME`为`libtest.so`将报错
* gcc编译动态库可以通过 `-Wl,-soname,libnative.so`选项指定`SONAME`

# 关于项目的使用
```shell
    git clone https://github.com/cntlb/native_activity.git
    git clone https://github.com/cntlb/NativeLauncher.git
```
先安装native_activity(或直接安装[app-debug_v1.0.apk](https://github.com/cntlb/native_activity/releases/download/v1.0/app-debug.apk))后启动NativeLauncher

# issue
正常启动后下一次启动会崩溃,如此循环, 待修复.