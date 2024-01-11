package com.enesas.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enesas.besinlerkitabi.R

// Bu utility dosyası eklentiler içindir. Yani herhangi bir sınıfa yada daha önce yazdığımız sınıflara ekleme yapabildiğimiz utility'leri burada toplayabiliriz.
// Glide ile daha kolay iş yapmak için eklenti yapacağız. Yapmak zorunda değiliz ama 1 kere yazınca hep bunu kullanabiliriz.

fun ImageView.gorselIndir(url: String?,placeholder: CircularProgressDrawable) {

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round) // hata olup bir şey yüklenemezse error ile bişey gösterdik.

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun placeHolderYap(context: Context): CircularProgressDrawable{ // Yani fotoğraf yüklenirken ne gösterilsin?
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f // Yaptığımız yuvarlak dönen görünümün kalınlığını vereceğiz. Float istiyor.
        centerRadius = 40f // Yaptığımız yuvarlak dönen görünümün radius vereceğiz. Float istiyor.
        start()
    }
}