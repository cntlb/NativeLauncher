package com.example.launcher.config

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.*
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.UserHandle

open class PackageManagerWrapper(val pm: PackageManager) : PackageManager() {
    override fun canonicalToCurrentPackageNames(names: Array<out String>?): Array<String> {
        return pm.canonicalToCurrentPackageNames(names)
    }

    override fun getLaunchIntentForPackage(packageName: String?): Intent {
        return pm.getLaunchIntentForPackage(packageName)
    }

    override fun getResourcesForApplication(app: ApplicationInfo?): Resources {
        return pm.getResourcesForApplication(app)
    }

    override fun getResourcesForApplication(appPackageName: String?): Resources {
        return pm.getResourcesForApplication(appPackageName)
    }

    override fun getProviderInfo(component: ComponentName?, flags: Int): ProviderInfo {
        return pm.getProviderInfo(component, flags)
    }

    override fun getReceiverInfo(component: ComponentName?, flags: Int): ActivityInfo {
        return pm.getReceiverInfo(component, flags)
    }

    override fun queryIntentActivityOptions(
        caller: ComponentName?,
        specifics: Array<out Intent>?,
        intent: Intent?,
        flags: Int
    ): MutableList<ResolveInfo> {
        return pm.queryIntentActivityOptions(caller, specifics, intent, flags)
    }

    override fun clearPackagePreferredActivities(packageName: String?) {
        pm.clearPackagePreferredActivities(packageName)
    }

    override fun getPackageInstaller(): PackageInstaller {
        return pm.getPackageInstaller()
    }

    override fun resolveService(intent: Intent?, flags: Int): ResolveInfo {
        return pm.resolveService(intent, flags)
    }

    override fun verifyPendingInstall(id: Int, verificationCode: Int) {
        pm.verifyPendingInstall(id, verificationCode)
    }

    override fun getApplicationIcon(info: ApplicationInfo?): Drawable {
        return pm.getApplicationIcon(info)
    }

    override fun getApplicationIcon(packageName: String?): Drawable {
        return pm.getApplicationIcon(packageName)
    }

    override fun extendVerificationTimeout(id: Int, verificationCodeAtTimeout: Int, millisecondsToDelay: Long) {
        pm.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay)
    }

    override fun getText(packageName: String?, resid: Int, appInfo: ApplicationInfo?): CharSequence {
        return pm.getText(packageName, resid, appInfo)
    }

    override fun resolveContentProvider(name: String?, flags: Int): ProviderInfo {
        return pm.resolveContentProvider(name, flags)
    }

    override fun getApplicationEnabledSetting(packageName: String?): Int {
        return pm.getApplicationEnabledSetting(packageName)
    }

    override fun queryIntentServices(intent: Intent?, flags: Int): MutableList<ResolveInfo> {
        return pm.queryIntentServices(intent, flags)
    }

    override fun hasSystemFeature(name: String?): Boolean {
        return pm.hasSystemFeature(name)
    }

    override fun getInstrumentationInfo(className: ComponentName?, flags: Int): InstrumentationInfo {
        return pm.getInstrumentationInfo(className, flags)
    }

    override fun getInstalledApplications(flags: Int): MutableList<ApplicationInfo> {
        return pm.getInstalledApplications(flags)
    }

    override fun getUserBadgedDrawableForDensity(
        drawable: Drawable?,
        user: UserHandle?,
        badgeLocation: Rect?,
        badgeDensity: Int
    ): Drawable {
        return pm.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity)
    }

    override fun checkPermission(permName: String?, pkgName: String?): Int {
        return pm.checkPermission(permName, pkgName)
    }

    override fun getDefaultActivityIcon(): Drawable {
        return pm.getDefaultActivityIcon()
    }

    override fun getPreferredPackages(flags: Int): MutableList<PackageInfo> {
        return pm.getPreferredPackages(flags)
    }

    override fun checkSignatures(pkg1: String?, pkg2: String?): Int {
        return pm.checkSignatures(pkg1, pkg2)
    }

    override fun checkSignatures(uid1: Int, uid2: Int): Int {
        return pm.checkSignatures(uid1, uid2)
    }

    override fun addPreferredActivity(
        filter: IntentFilter?,
        match: Int,
        set: Array<out ComponentName>?,
        activity: ComponentName?
    ) {
        pm.addPreferredActivity(filter, match, set, activity)
    }

    override fun removePackageFromPreferred(packageName: String?) {
        pm.removePackageFromPreferred(packageName)
    }

    override fun queryIntentActivities(intent: Intent?, flags: Int): MutableList<ResolveInfo> {
        return pm.queryIntentActivities(intent, flags)
    }

    override fun addPermission(info: PermissionInfo?): Boolean {
        return pm.addPermission(info)
    }

    override fun getActivityBanner(activityName: ComponentName?): Drawable {
        return pm.getActivityBanner(activityName)
    }

    override fun getActivityBanner(intent: Intent?): Drawable {
        return pm.getActivityBanner(intent)
    }

    override fun getDrawable(packageName: String?, resid: Int, appInfo: ApplicationInfo?): Drawable {
        return pm.getDrawable(packageName, resid, appInfo)
    }

    override fun setComponentEnabledSetting(componentName: ComponentName?, newState: Int, flags: Int) {
        pm.setComponentEnabledSetting(componentName, newState, flags)
    }

    override fun getApplicationInfo(packageName: String?, flags: Int): ApplicationInfo {
        return pm.getApplicationInfo(packageName, flags)
    }

    override fun resolveActivity(intent: Intent?, flags: Int): ResolveInfo {
        return pm.resolveActivity(intent, flags)
    }

    override fun queryBroadcastReceivers(intent: Intent?, flags: Int): MutableList<ResolveInfo> {
        return pm.queryBroadcastReceivers(intent, flags)
    }

    override fun getXml(packageName: String?, resid: Int, appInfo: ApplicationInfo?): XmlResourceParser {
        return pm.getXml(packageName, resid, appInfo)
    }

    override fun getPackageInfo(packageName: String?, flags: Int): PackageInfo {
        return pm.getPackageInfo(packageName, flags)
    }

    override fun getPackagesHoldingPermissions(permissions: Array<out String>?, flags: Int): MutableList<PackageInfo> {
        return pm.getPackagesHoldingPermissions(permissions, flags)
    }

    override fun addPermissionAsync(info: PermissionInfo?): Boolean {
        return pm.addPermissionAsync(info)
    }

    override fun getSystemAvailableFeatures(): Array<FeatureInfo> {
        return pm.getSystemAvailableFeatures()
    }

    override fun getActivityLogo(activityName: ComponentName?): Drawable {
        return pm.getActivityLogo(activityName)
    }

    override fun getActivityLogo(intent: Intent?): Drawable {
        return pm.getActivityLogo(intent)
    }

    override fun getSystemSharedLibraryNames(): Array<String> {
        return pm.getSystemSharedLibraryNames()
    }

    override fun queryPermissionsByGroup(group: String?, flags: Int): MutableList<PermissionInfo> {
        return pm.queryPermissionsByGroup(group, flags)
    }

    override fun queryIntentContentProviders(intent: Intent?, flags: Int): MutableList<ResolveInfo> {
        return pm.queryIntentContentProviders(intent, flags)
    }

    override fun getApplicationBanner(info: ApplicationInfo?): Drawable {
        return pm.getApplicationBanner(info)
    }

    override fun getApplicationBanner(packageName: String?): Drawable {
        return pm.getApplicationBanner(packageName)
    }

    override fun queryContentProviders(processName: String?, uid: Int, flags: Int): MutableList<ProviderInfo> {
        return pm.queryContentProviders(processName, uid, flags)
    }

    override fun getPackageGids(packageName: String?): IntArray {
        return pm.getPackageGids(packageName)
    }

    override fun getResourcesForActivity(activityName: ComponentName?): Resources {
        return pm.getResourcesForActivity(activityName)
    }

    override fun getPackagesForUid(uid: Int): Array<String> {
        return pm.getPackagesForUid(uid)
    }

    override fun getPermissionGroupInfo(name: String?, flags: Int): PermissionGroupInfo {
        return pm.getPermissionGroupInfo(name, flags)
    }

    override fun getPermissionInfo(name: String?, flags: Int): PermissionInfo {
        return pm.getPermissionInfo(name, flags)
    }

    override fun removePermission(name: String?) {
        pm.removePermission(name)
    }

    override fun queryInstrumentation(targetPackage: String?, flags: Int): MutableList<InstrumentationInfo> {
        return pm.queryInstrumentation(targetPackage, flags)
    }

    override fun addPackageToPreferred(packageName: String?) {
        pm.addPackageToPreferred(packageName)
    }

    override fun currentToCanonicalPackageNames(names: Array<out String>?): Array<String> {
        return pm.currentToCanonicalPackageNames(names)
    }

    override fun getComponentEnabledSetting(componentName: ComponentName?): Int {
        return pm.getComponentEnabledSetting(componentName)
    }

    override fun getLeanbackLaunchIntentForPackage(packageName: String?): Intent {
        return pm.getLeanbackLaunchIntentForPackage(packageName)
    }

    override fun getInstalledPackages(flags: Int): MutableList<PackageInfo> {
        return pm.getInstalledPackages(flags)
    }

    override fun getUserBadgedIcon(icon: Drawable?, user: UserHandle?): Drawable {
        return pm.getUserBadgedIcon(icon, user)
    }

    override fun getAllPermissionGroups(flags: Int): MutableList<PermissionGroupInfo> {
        return pm.getAllPermissionGroups(flags)
    }

    override fun getActivityInfo(component: ComponentName?, flags: Int): ActivityInfo {
        return pm.getActivityInfo(component, flags)
    }

    override fun getNameForUid(uid: Int): String {
        return pm.getNameForUid(uid)
    }

    override fun getApplicationLogo(info: ApplicationInfo?): Drawable {
        return pm.getApplicationLogo(info)
    }

    override fun getApplicationLogo(packageName: String?): Drawable {
        return pm.getApplicationLogo(packageName)
    }

    override fun getApplicationLabel(info: ApplicationInfo?): CharSequence {
        return pm.getApplicationLabel(info)
    }

    override fun getPreferredActivities(
        outFilters: MutableList<IntentFilter>?,
        outActivities: MutableList<ComponentName>?,
        packageName: String?
    ): Int {
        return pm.getPreferredActivities(outFilters, outActivities, packageName)
    }

    override fun isSafeMode(): Boolean {
        return pm.isSafeMode()
    }

    override fun setInstallerPackageName(targetPackage: String?, installerPackageName: String?) {
        pm.setInstallerPackageName(targetPackage, installerPackageName)
    }

    override fun getUserBadgedLabel(label: CharSequence?, user: UserHandle?): CharSequence {
        return pm.getUserBadgedLabel(label, user)
    }

    override fun getInstallerPackageName(packageName: String?): String {
        return pm.getInstallerPackageName(packageName)
    }

    override fun setApplicationEnabledSetting(packageName: String?, newState: Int, flags: Int) {
        pm.setApplicationEnabledSetting(packageName, newState, flags)
    }

    override fun getServiceInfo(component: ComponentName?, flags: Int): ServiceInfo {
        return pm.getServiceInfo(component, flags)
    }

    override fun getActivityIcon(activityName: ComponentName?): Drawable {
        return pm.getActivityIcon(activityName)
    }

    override fun getActivityIcon(intent: Intent?): Drawable {
        return pm.getActivityIcon(intent)
    }

    override fun getInstantAppCookie(): ByteArray {
        return pm.getInstantAppCookie()
    }

    override fun hasSystemFeature(name: String?, version: Int): Boolean {
        return pm.hasSystemFeature(name, version)
    }

    override fun isPermissionRevokedByPolicy(permName: String?, pkgName: String?): Boolean {
        return pm.isPermissionRevokedByPolicy(permName, pkgName)
    }

    override fun getInstantAppCookieMaxBytes(): Int {
        return pm.getInstantAppCookieMaxBytes()
    }

    override fun getSharedLibraries(flags: Int): MutableList<SharedLibraryInfo> {
        return pm.getSharedLibraries(flags)
    }

    override fun getChangedPackages(sequenceNumber: Int): ChangedPackages {
        return pm.getChangedPackages(sequenceNumber)
    }

    override fun getPackageInfo(versionedPackage: VersionedPackage?, flags: Int): PackageInfo {
        return pm.getPackageInfo(versionedPackage, flags)
    }

    override fun getPackageGids(packageName: String?, flags: Int): IntArray {
        return pm.getPackageGids(packageName, flags)
    }

    override fun clearInstantAppCookie() {
        pm.clearInstantAppCookie()
    }

    override fun getPackageUid(packageName: String?, flags: Int): Int {
        return pm.getPackageUid(packageName, flags)
    }

    override fun updateInstantAppCookie(cookie: ByteArray?) {
        pm.updateInstantAppCookie(cookie)
    }

    override fun setApplicationCategoryHint(packageName: String?, categoryHint: Int) {
        pm.setApplicationCategoryHint(packageName, categoryHint)
    }

    override fun canRequestPackageInstalls(): Boolean {
        return pm.canRequestPackageInstalls()
    }

    override fun isInstantApp(): Boolean {
        return pm.isInstantApp()
    }

    override fun isInstantApp(packageName: String?): Boolean {
        return pm.isInstantApp(packageName)
    }
}