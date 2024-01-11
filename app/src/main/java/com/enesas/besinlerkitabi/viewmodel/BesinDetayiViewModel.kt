package com.enesas.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesas.besinlerkitabi.model.Besin
import com.enesas.besinlerkitabi.servis.BesinDAODatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application): BaseViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){ // room sqlite kullanmak için geliştirilen bir kütüphane.

        launch {
            val dao = BesinDAODatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }

    }
}