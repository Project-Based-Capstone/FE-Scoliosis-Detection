# Mobile Development: Scolio-lysis, early scoliosis detection application.
![Banner](https://user-images.githubusercontent.com/58366968/173171200-fcba3420-ee06-4fe2-bd7a-5c8140480435.png)


## App Description
Scoliosis is a medical condition when there is a change in the shape of the spint that occurs when people grow, of which, in 90 percent of cases, the cause is unknown. Scoliosis is a progressive condition and it does tend to get worse as people age. That is why early detection is the key. Early detection of scoliosis can help to prevent progression of the condition. This application is a simplified scoliosis detection that can assist and help users in detecting the uncommon body alignment early.


* Download Apk : [https://drive.google.com/drive/folders/1fot7DyP3mVG0_hxy3fW6ypmgij_tkcZO?usp=sharing](https://bit.ly/3Scolio-lysis)


### Screenshots
![Group 47](https://user-images.githubusercontent.com/58366968/173171368-282350d3-dd69-4a7c-8c82-5247fda4c0aa.png)


## Development

#### Requirements
* A Mac or Windows computer.
* Android Studio
* Android Virtual Device (AVD) > API 21

#### Dependencies
```Gradle
    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.4.2'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    implementation "androidx.datastore:datastore-preferences:1.0.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1"
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6"
    implementation "androidx.security:security-crypto:1.1.0-alpha03"
    implementation 'com.github.bumptech.glide:glide:4.13.1'

    implementation "androidx.camera:camera-camera2:1.2.0-alpha02"
    implementation "androidx.camera:camera-lifecycle:1.2.0-alpha02"
    implementation "androidx.camera:camera-view:1.2.0-alpha02"
```

#### Plugins
```
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-parcelize'
    id 'kotlin-kapt'
```
### Clone this App

**Clone**
```bash
$ git clone https://github.com/Project-Based-Capstone/FE-Scoliosis-Detection.git
```

**Open in Android Studio**
* `File -> Open -> Navigate to folder that you clone this repo -> Open`

**Run this project on AVD**
* `Start AVD -> Run 'app'`

**Build this app**
* `Build -> Build Bundle(s)/APK(s) -> Build APK(s)`
