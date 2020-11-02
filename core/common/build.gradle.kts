plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.junitDep)
    implementation(Dependencies.coroutineTestDep) {
        //https://github.com/Kotlin/kotlinx.coroutines/issues/2023#issuecomment-665150312
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
    api(Dependencies.glideDep)
    api(Dependencies.pagingDep)
    api(Dependencies.lifecycleLiveDataDep)
    sharedDesign()
    sharedAppCompat()
    sharedDaggerHilt()
    sharedHiltLifecycle()
    sharedNavigationArch()
}