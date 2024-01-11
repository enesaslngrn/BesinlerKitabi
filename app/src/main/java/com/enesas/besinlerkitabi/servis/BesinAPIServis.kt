package com.enesas.besinlerkitabi.servis

import com.enesas.besinlerkitabi.model.Besin
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis { // Bu da retrofit için. Burada retrofit builder oluşturağız.

    //BASE URL -> https://raw.githubusercontent.com/
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // bu satır retrofit convert etmek için
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Bu satır rxjava için.
        .build()
        .create(BesinAPI::class.java)

    fun getData(): Single<List<Besin>> { // BesinAPI interface'te oluşturduğumuz fonksiyonu burada döndürerek verileri alabiliyoruz.
        // Yani arayüzümüzü buraya bağladık. Bu fonksiyon sayesinde ViewModel'ların içerisinde rahatlıkla çalışıcaz.
        return api.getBesin()
    }
}