import Dependencies.daggerHiltProcessorDep
import Dependencies.hiltProcessorDep

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
    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }

    dynamicFeatures = mutableSetOf(":features:homefeature", ":features:articlefeature",
        ":features:achievedfeature", ":features:detailFeature"
    )
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(Dependencies.kotlinDep)
    /*kapt(daggerHiltProcessorDep)
    kapt(hiltProcessorDep)*/
    network()
    playCore()
    integrationTest()
    unitTest()
    //design()
    //navigationArch()
    //appCompat()
    daggerHilt()
    hiltLifecycle()
}
