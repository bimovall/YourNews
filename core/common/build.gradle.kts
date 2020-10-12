plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.junitDep)
    implementation(Dependencies.coroutineTestDep)
    api(Dependencies.glideDep)
    api(Dependencies.pagingDep)
    api(Dependencies.lifecycleLiveDataDep)
    sharedDesign()
    sharedAppCompat()
    sharedDaggerHilt()
    sharedHiltLifecycle()
    sharedNavigationArch()
}