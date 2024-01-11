package com.enesas.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesas.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.enesas.besinlerkitabi.databinding.FragmentBesinListesiBinding
import com.enesas.besinlerkitabi.viewmodel.BesinListesiViewModel


class BesinListesiFragment : Fragment() {
    private lateinit var binding: FragmentBesinListesiBinding
    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBesinListesiBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        //Adapter'ı bağlayalım.
        binding.besinListesiRecyclerView.layoutManager = LinearLayoutManager(context) // Eğer aktivite içinde olsaydık direkt this derdik.
        binding.besinListesiRecyclerView.adapter = recyclerBesinAdapter

        binding.swipeRefreshLayout.setOnRefreshListener { // Kullanıcı aşağı çekip refresh ederse ne olsun ->
            binding.besinYukleniyor.visibility = View.VISIBLE
            binding.besinHataMesaji.visibility = View.GONE
            binding.besinListesiRecyclerView.visibility = View.GONE

            viewModel.refreshFromInternet() // Tekrar refreshData fonksiyonu çağırarak internetten tekrar veri çektirdik "swipe"edince.
            binding.swipeRefreshLayout.isRefreshing = false // bunu diyerek swipelayout'un kendi çıkan refresh bar'ını devre dışı bıraktık ki bizimki ile birlikte 2 tane olmasın.
        }

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer {besinler ->
            besinler?.let {

                binding.besinListesiRecyclerView.visibility = View.VISIBLE // Böylece recyclerview görünür oldu yani alt alta görünecek veriler.
                recyclerBesinAdapter.besinListesiniGuncelle(it)
            }
        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer {hata ->

            hata?.let {
                if (it){
                    binding.besinHataMesaji.visibility = View.VISIBLE
                    binding.besinListesiRecyclerView.visibility = View.GONE
                }else{
                    binding.besinHataMesaji.visibility = View.GONE
                }
            }

        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let{
                if (it){
                    binding.besinListesiRecyclerView.visibility = View.GONE
                    binding.besinHataMesaji.visibility = View.GONE
                    binding.besinYukleniyor.visibility = View.VISIBLE
                }else{
                    binding.besinYukleniyor.visibility = View.GONE
                }
            }

        })
    }
}