plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.finalprojectwmb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalprojectwmb"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.picasso)
    implementation(platform("com.google.firebase:firebase-bom:33.5.1")) // Use BOM for Firebase
    implementation("com.google.firebase:firebase-auth") // No need to specify version, BOM handles it
    implementation("com.google.firebase:firebase-firestore") // No need to specify version, BOM handles it
    implementation("com.google.firebase:firebase-storage") // No need to specify version, BOM handles it
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.osmdroid:osmdroid-android:6.1.10")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    // Add Google Play Services Location
    implementation("com.google.android.gms:play-services-location:21.0.1") // Check for the latest version
}
