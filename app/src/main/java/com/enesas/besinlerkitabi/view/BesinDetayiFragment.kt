package com.enesas.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enesas.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.enesas.besinlerkitabi.util.gorselIndir
import com.enesas.besinlerkitabi.util.placeHolderYap
import com.enesas.besinlerkitabi.viewmodel.BesinDetayiViewModel


class BesinDetayiFragment : Fragment() {
   private lateinit var binding: FragmentBesinDetayiBinding
   private lateinit var viewModel: BesinDetayiViewModel
   private var besinId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBesinDetayiBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{ // Yani argumanlar bana bir bundle olarak geldiyse buraya gir ve işlemleri yap.
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }
        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let{
                binding.besinIsim.text = it.besinIsim
                binding.besinKalori.text = it.besinKalori
                binding.besinKarbonhidrat.text = it.besinKarbonhidrat
                binding.besinProtein.text = it.besinProtein
                binding.besinYag.text = it.besinYag

                context?.let {
                    binding.besinImageView.gorselIndir(besin.besinGorsel, placeHolderYap(it))
                }

            }

        })
    }
}