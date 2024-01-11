package com.enesas.besinlerkitabi.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enesas.besinlerkitabi.model.Besin

@Dao
interface BesinDAO { // DAO demek -> Data Access Object. Room kütüphanesi ile gelen bir şey. Şimdi burada gelişmiş Sqlite kullanacağız.

    @Insert
    suspend fun insertAll(vararg besin : Besin): List<Long>
        // bu Suspend fonksiyonlar "kotlin coroutines" ile gelen bir şey. Uzun süren işlemleri daha optimize yapmak için.
        // Bu vararg aldığımız arguments 1 den fazla ve istediğimiz kadar olabiliyor. Yani 100 tane besin de yazsak olur.
        // List<Long> olacak listenin içinde çünkü Besin.kt'de otomatik oluştutulan uuid'ler long olarak gelecek.


    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>

    @Query("SELECT * FROM besin WHERE uuid = :besinId ")
    suspend fun getBesin(besinId : Int): Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()

}