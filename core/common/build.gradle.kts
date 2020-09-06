plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Dependencies.glideDep)
    sharedDesign()
    sharedAppCompat()
    sharedDaggerHilt()
    sharedHiltLifecycle()
    sharedNavigationArch()
}