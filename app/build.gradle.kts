import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias (libs.plugins.kotlin.parcelize)
    alias (libs.plugins.safe.args)
}

android {
    namespace = "com.yyy.listview"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yyy.listview"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String", "POSTS_BASE_URL", gradleLocalProperties(
                    rootDir
                ).getProperty("posts_base_url")
            )
        }
        debug {
            buildConfigField(
                "String", "POSTS_BASE_URL", gradleLocalProperties(
                    rootDir
                ).getProperty("posts_base_url")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //dependincy injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)

    //converter
    implementation(libs.gson)
    implementation(libs.converter.gson)

    //interceptor
    implementation(libs.retrofit)
    implementation(libs.loging.interceptor)

    implementation(libs.navigation.ktx)
    implementation(libs.navigation.fragment)

    implementation(libs.coroutines)
    implementation(libs.coroutines.test)

    implementation(libs.coil)

    //loging
    implementation(libs.timber)

    androidTestImplementation(libs.and.core.testing)
    androidTestImplementation(libs.truth.assertion)
    androidTestImplementation(libs.turbine.testing)
    testImplementation(libs.truth.assertion)


}