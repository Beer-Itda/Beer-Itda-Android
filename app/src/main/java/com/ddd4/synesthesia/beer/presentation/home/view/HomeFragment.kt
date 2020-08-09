package com.ddd4.synesthesia.beer.presentation.home.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.base.BaseFragment
import com.ddd4.synesthesia.beer.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.home_to_detail)
        }
    }
}