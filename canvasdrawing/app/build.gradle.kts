plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // kapt
    id ("kotlin-kapt")

    // parcelable
    id ("kotlin-parcelize")

    // room
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"

    // navigation
    id ("androidx.navigation.safeargs.kotlin") version "2.5.2"

    // google services
    id("com.google.gms.google-services")
}

android {
    namespace = "com.victoris.canvas_drawing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.victoris.canvas_drawing"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "62.812.3275.0669"

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Room libraries
    implementation("androidx.room:room-runtime:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")

    // LiveData libraries
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    // ViewModel libraries
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // Fragment libraries
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    // Navigation libraries
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    // BoM
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    // Auth
    implementation("com.google.firebase:firebase-auth-ktx")
    // Database and Storage
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
}