package com.enesas.besinlerkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity // Bu Entity Room'dan gelen bir constructor. Bunu yapınca sqlite içindeki table'ın adı Besin oldu.

data class Besin(
    @ColumnInfo("isim") // Bu da Room için. Çektiğimiz verideki isim ve buradaki farklı olabileceği için column belirtmeliyiz.
    @SerializedName("isim") // Bu serializedname'ler retrofit için yani internetten veriyi çekerken isimleri eşitlemek için.
    val besinIsim: String?,
    @ColumnInfo("kalori")
    @SerializedName("kalori")
    val besinKalori: String?,
    @ColumnInfo("karbonhidrat")
    @SerializedName("karbonhidrat")
    val besinKarbonhidrat: String?,
    @ColumnInfo("protein")
    @SerializedName("protein")
    val besinProtein: String?,
    @ColumnInfo("yag")
    @SerializedName("yag")
    val besinYag: String?,
    @ColumnInfo("gorsel")
    @SerializedName("gorsel")
    val besinGorsel: String?) {

    //internetten yada bir sunucudan bir veri çekip onunla bir model oluşturmak istiyorsak data class kullanırız.
    //https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json buradaki verileri çekeceğiz.

    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0

}