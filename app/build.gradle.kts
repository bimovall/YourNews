plugins {
    `android-base`
}

android {
    compileSdkVersion(Versions.targetSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)
    defaultConfig {
        applicationId = "bivano.apps.yournews"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dynamicFeatures = mutableSetOf(":features:homefeature", ":features:articlefeature",
        ":features:achievedfeature"
    )
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:common"))
    implementation(Dependencies.kotlinDep)
    network()
    playCore()
    design()
    navigationArch()
    appCompat()
    integrationTest()
    unitTest()
    daggerHilt()
}
