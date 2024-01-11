package com.enesas.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesas.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.enesas.besinlerkitabi.model.Besin
import com.enesas.besinlerkitabi.util.gorselIndir
import com.enesas.besinlerkitabi.util.placeHolderYap
import com.enesas.besinlerkitabi.view.BesinListesiFragmentDirections
import com.squareup.picasso.Picasso

class BesinRecyclerAdapter(val besinListesi: ArrayList<Besin>): RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {
    class BesinViewHolder(val binding: BesinRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.isim.text = besinListesi.get(position).besinIsim
        holder.binding.kalori.text = besinListesi.get(position).besinKalori
        // Normalde burada picasso ile görseli eklemeyi öğrendik. Ama bu sefer Glide ile yapıcaz. Bunu yaparken util oluşturduk, oradan alacağız.
        holder.binding.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeHolderYap(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){

        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
}