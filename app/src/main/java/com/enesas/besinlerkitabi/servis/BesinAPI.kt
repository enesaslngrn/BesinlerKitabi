package com.enesas.besinlerkitabi.servis

import com.enesas.besinlerkitabi.model.Besin
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface BesinAPI { // Bu kısım retrofit için.

    //GET ve POST istekleri sunucudan verileri çekmek ve yazmak için kullanılır.

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // Retrofit bizden url'yi 2ye bölerek kullanmayı istiyor. Direkt verileri çekeceğin url'yi yapıştıramıyorsun.
    // O yüzden bir base url'ye ihtiyacın var -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    /*
    Normalde sadece Retrofit kullanarak bunu yapabiliriz.
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Call<List<Besin>>
     */

    // Ama biz ReactiveX yani rxjava da öğrenmek için farklı bir şekilde devam edeceğiz. Rxjava daha karmaşık ama daha detaylı iş yapılıyor. Yani retrofit ile de yapılabilir.
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>

    //Rxjava da Single<T>, Observable<T>, Flowable<T> gibi çeşitli amaçlara hizmet eden fonksiyonlar var. Bize burada single gerek yani bir veriyi çekiyor ve ardından duruyor.
}