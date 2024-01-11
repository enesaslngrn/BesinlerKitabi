package com.enesas.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {

    // bu view model class'ı coroutine kullanıp threading'i kolaylaştırmak için açtık.
    // normalde direkt BesinListesiViewModel içerisinde bu işlemleri yapabilirdik. Ama bunu açtık ki diğer tüm viewmodellar'da çağrabilelim.

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel() // Viewmodel ile işler bitince job'ında işi bitsin diye bu fonksiyonu açtık.
    }

}