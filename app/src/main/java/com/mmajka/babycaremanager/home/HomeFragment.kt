package com.mmajka.babycaremanager.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.*
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.actions.ActionFragment
import com.mmajka.babycaremanager.databinding.HomeFragmentBinding
import com.mmajka.babycaremanager.diaper.DiaperFragment
import com.mmajka.babycaremanager.feeding.FeedingFragment

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var container = R.id.fragment_container
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getChild(binding.babyName, binding.age)
        binding.inventoryRv.diaper.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(container, DiaperFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.bath.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.feeding.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(container, FeedingFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.sleep.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.walk.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }
    }

}