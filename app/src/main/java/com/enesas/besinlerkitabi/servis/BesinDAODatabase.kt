package com.enesas.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesas.besinlerkitabi.model.Besin

@Database(entities = arrayOf(Besin::class), version = 1)
abstract class BesinDAODatabase : RoomDatabase() { //Gelecekte çok karmaşık uygulamalar yazarız diye ve threading yaparken çakışmalar olmasın diye, singleton yapısı ile bu database'i en ideal şekilde yazacağız.

    abstract fun besinDao() : BesinDAO

    //Singleton, tek bir objeye ulaşabildiğimiz sınıflar. Bunu Companion object ile yapıyorduk.

    companion object{

        @Volatile // bu Volatile ekleyince diğer thread'lere görünür oluyor bu instance.
        private var INSTANCE : BesinDAODatabase? = null

        private val lock  = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock){ // invoke() fonksyionu -> Bu sınıftan daha önce bir nesne oluşturulduysa onu döndür ve bunu senkronize yap.
            INSTANCE ?: databaseOlustur(context).also {
                INSTANCE = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDAODatabase::class.java, "besindatabase").build()

    }

}