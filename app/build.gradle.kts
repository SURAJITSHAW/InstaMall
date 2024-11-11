plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.example.instamall"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.instamall"
        minSdk = 29
        targetSdk = 34
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Views/Fragments integration
    val nav_version = "2.8.3"

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)


    // Glide (for image loading and caching)
    implementation(libs.glide)
    kapt(libs.compiler)

    // Circular Image View (for displaying images in a circular shape)
    implementation(libs.circleimageview)


    // ViewPager2 Indicator (for displaying indicators with ViewPager2)
    implementation(libs.dotsindicator)

    // Dagger Hilt (for dependency injection)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coroutines (for asynchronous programming)
    implementation(libs.kotlinx.coroutines.android)
}