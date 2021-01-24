# MovieListApp
This is a movie list application. You can see both upcoming movie details and top rated movie details.

  Architecture Used: MVVM.
  
 Jetpack Components: ViewModel, Livedata, UI Data Binding
 
 Language: Kotlin

Open-source libraries used:
//live data and view Model
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
    //Recycle view and card view
    implementation "androidx.recyclerview:recyclerview:1.1.0"
    implementation "androidx.cardview:cardview:1.0.0"
    implementation "com.google.android.material:material:1.3.0-rc01"
    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.9.0"

    //Gson
    implementation "com.google.code.gson:gson:2.8.6"
    //Glide
    implementation "com.github.bumptech.glide:glide:4.11.0"
    annotationProcessor "com.github.bumptech.glide:compiler:4.11.0"

    //Pagging
    implementation "androidx.paging:paging-runtime:2.1.2"

    //RX
    implementation "io.reactivex.rxjava2:rxjava:2.2.7"
