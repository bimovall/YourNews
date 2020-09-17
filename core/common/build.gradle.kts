plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Dependencies.glideDep)
    api(Dependencies.pagingDep)
    api(Dependencies.lifecycleLiveDataDep)
    sharedDesign()
    sharedAppCompat()
    sharedDaggerHilt()
    sharedHiltLifecycle()
    sharedNavigationArch()
}