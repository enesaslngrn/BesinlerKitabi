package com.enesas.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesas.besinlerkitabi.model.Besin
import com.enesas.besinlerkitabi.servis.BesinAPIServis
import com.enesas.besinlerkitabi.servis.BesinDAODatabase
import com.enesas.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>() // Mutable değiştirilebilir demek.
    val besinHataMesaji = MutableLiveData<Boolean>() // Eğer besin hata verdiyse hata mesajını görünür hale getir yoksa görünmez halde kalsın.
    val besinYukleniyor = MutableLiveData<Boolean>() // Eğer besin yükleniyorsa progress barı görünür hale getir yoksa görünmez halde kalsın.

    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable() // ne demek bu disposable? Kullan at bunlar. 1 kere kullan işin bitince at gitsin demek.
    // Bu rxjava'nın bir özelliği bu sayede lifecycle iyi çalışmış oluyor ve optimizasyon daha iyi oluyor.

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private val guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L // bunu 10 dakikanın nano saniyeye çevrilmiş hali gibi düşün. Yani 60 saniye normalde 60 *10^9 saniye ama biz 10 dakika istediğimiz için 10 ile çarptık

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl() // Bu bana ozelSahredPreference da aldığım zamanı getirecek.
        // şimdi kaç dakika geçtiğini öğrenip kaydetmemiz lazım. bunun için nano saniye metriklerini anlamak gerek.
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            // o zaman internetten almama gerek yok, verileri sqlite'dan çekeceğim
            verileriSqlitetanAl()
        }else{
            verileriInternettenAl()
        }
    }

    fun refreshFromInternet(){ // Bunu swipe yaptığımızda da internetten çeksin diye yapıyoruz. Ayrı bi refresh fonksiyonu yani
        verileriInternettenAl()
    }

    private fun verileriSqlitetanAl(){
        besinYukleniyor.value = true

        launch {
            val besinListesi = BesinDAODatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri roomdan aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        disposable.add( // Aynı fragment içinde belki 10 tane 20 tane istek yapacağız ileride o yüzden hep bu disposable'lar ile istekleri yapacağız.
            besinApiServis.getData() // şimdi burada belirtmek gerek 3 tane şey var. SubscribeOn, ObserveOn ve SubscribeWith
                .subscribeOn(Schedulers.newThread()) // bunun sayesinde oluşturduğumuz Single<T> observable'ına kayıt oluyoruz. Bunu asenkron şekilde arka planda yapmak için newThread açıyoruz.
                .observeOn(AndroidSchedulers.mainThread()) // Bunu ise kullanıcıya göstereceğimiz yerde yapmalıyız. Yani mainThread'de yapmalıyız.
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){ // DisposableSingleObserver abstract class olduğu için object : olarak kullanmamız gerekli.
                    override fun onSuccess(t: List<Besin>) {
                        //Başarılı olursak
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Besinleri internet'ten aldık",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace() // bunu demek faydalı. Eğer bir hata alırsak logcat'te görmek için
                    }

                })
        )
    }

    private fun besinleriGoster(besinlerListesi: List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>){
        // Buraya devam etmek için bir tane coroutine kullanacağımız sınıf oluşturmamız gerek. Adını BaseViewModel koydum.
        launch {

            val dao = BesinDAODatabase(getApplication()).besinDao()
            dao.deleteAllBesin() // Daha önce veri varsa hepsini bi silelim.
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())// bu liste içindeki işlemler tek tek ayrılacak bu toTypedArray ile. Başına yıldız koyarak tek tek alabiliyoruz.
            var i = 0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i += 1
            }
            besinleriGoster(besinListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}