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
        versionCode = 5
        versionName = "1.0.5"

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
        variant.outputs.forEach { output ->
            // [꼼꼼 체크] 타입 캐스팅 없이 직접 set() 메서드에 접근하는 가장 확실한 경로입니다.
            val versionName = android.defaultConfig.versionName ?: "1.0"
            val buildType = variant.buildType ?: "release"
            val newFileName = "autoCheckSample_v${versionName}_${buildType}.apk"

            // 이 한 줄이 핵심입니다. 복잡한 캐스팅 없이 속성만 명확하게 지정합니다.
            (output as com.android.build.api.variant.impl.VariantOutputImpl).outputFileName.set(newFileName)
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