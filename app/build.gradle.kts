plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.autochecksample"
    // compileSdk 설정 방식도 최신 표준인 숫자 지정 방식으로 깔끔하게 정리했습니다.
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.autochecksample"
        minSdk = 24
        targetSdk = 36
        versionCode = 7
        versionName = "1.0.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

androidComponents {
    onVariants { variant ->
        // 1. APK 이름 변경 (기존 성공 로직)
        variant.outputs.forEach { output ->
            val versionName = android.defaultConfig.versionName ?: "1.0"
            val buildType = variant.buildType ?: "release"
            val newFileName = "autoCheckSample_v${versionName}_${buildType}.apk"
            (output as com.android.build.api.variant.impl.VariantOutputImpl).outputFileName.set(newFileName)
        }

        // 2. AAB 이름 변경 (태스크 존재 여부를 꼼꼼하게 확인)
        val versionName = android.defaultConfig.versionName ?: "1.0"
        val buildType = variant.buildType ?: "release"
        val bundleTaskName = "bundle${variant.name.replaceFirstChar { it.uppercase() }}"

        // named 대신 matching을 사용해 태스크가 생성된 경우에만 설정을 추가합니다.
        tasks.matching { it.name == bundleTaskName }.configureEach {
            doLast {
                val bundleDir = File("${project.layout.buildDirectory.get()}/outputs/bundle/${buildType}")
                val defaultFile = File(bundleDir, "app-${buildType}.aab")
                val newFile = File(bundleDir, "autoCheckSample_v${versionName}_${buildType}.aab")

                if (defaultFile.exists()) {
                    if (defaultFile.renameTo(newFile)) {
                        println("### AAB 이름 변경 완료: ${newFile.name}")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}