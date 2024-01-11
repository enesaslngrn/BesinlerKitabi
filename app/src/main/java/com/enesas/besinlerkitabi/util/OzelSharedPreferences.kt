package com.enesas.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class OzelSharedPreferences {

    companion object{

        private var sharedPreferences: SharedPreferences? = null
        private val ZAMAN = "zaman"

        @Volatile
        private var INSTANCE : OzelSharedPreferences? = null
        private val lock = Any()
        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context : Context) : OzelSharedPreferences = INSTANCE ?: synchronized(lock){
            INSTANCE ?: ozelSharedPreferencesYap(context).also {
                INSTANCE = it
            }
        }

        private fun ozelSharedPreferencesYap(context : Context): OzelSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }

    fun zamaniKaydet(zaman : Long){

        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }

    fun zamaniAl() = sharedPreferences?.getLong(ZAMAN,0)

}