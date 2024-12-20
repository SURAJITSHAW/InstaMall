plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
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
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.firestore)
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
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    // Coroutines (for asynchronous programming)
    implementation(libs.kotlinx.coroutines.android)

//    
    implementation("me.relex:circleindicator:2.1.6")

//    razorpay
    implementation(libs.checkout)
    implementation(libs.checkout.v1623)


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}