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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    dynamicFeatures = mutableSetOf(
        ":features:homefeature", ":features:articlefeature",
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
    network()
    playCore()
    integrationTest()
    unitTest()
    room()
    daggerHilt()
    hiltLifecycle()
}

//https://github.com/android/app-bundle-samples/blob/master/DynamicFeatures/app/src/main/java/com/google/android/samples/dynamicfeatures/MainActivity.kt
val bundletoolJar = project.rootDir.resolve("library/bundletool/bundletool-all-1.2.0.jar")
android.applicationVariants.all(object : Action<com.android.build.gradle.api.ApplicationVariant> {
    override fun execute(variant: com.android.build.gradle.api.ApplicationVariant) {
        variant.outputs.forEach { output: com.android.build.gradle.api.BaseVariantOutput? ->
            (output as? com.android.build.gradle.api.ApkVariantOutput)?.let { apkOutput: com.android.build.gradle.api.ApkVariantOutput ->
                var filePath = apkOutput.outputFile.absolutePath
                filePath = filePath.replaceAfterLast(".", "aab")
                filePath = filePath.replace("build/outputs/apk/", "build/outputs/bundle/")
                var outputPath = filePath.replace("build/outputs/bundle/", "build/outputs/apks/")
                outputPath = outputPath.replaceAfterLast(".", "apks")

                tasks.register<JavaExec>("buildApks${variant.name.capitalize()}") {
                    classpath = files(bundletoolJar)
                    args = listOf(
                        "build-apks",
                        "--overwrite",
                        "--local-testing",
                        "--bundle",
                        filePath,
                        "--output",
                        outputPath
                    )
                    dependsOn("bundle${variant.name.capitalize()}")
                }

                tasks.register<JavaExec>("installApkSplitsForTest${variant.name.capitalize()}") {
                    classpath = files(bundletoolJar)
                    args = listOf("install-apks", "--apks", outputPath)
                    dependsOn("buildApks${variant.name.capitalize()}")
                }
            }
        }
    }
})

