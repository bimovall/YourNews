plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Dependencies.glideDep)
    api(Dependencies.pagingDep)
    sharedDesign()
    sharedAppCompat()
    sharedDaggerHilt()
    sharedHiltLifecycle()
    sharedNavigationArch()
}