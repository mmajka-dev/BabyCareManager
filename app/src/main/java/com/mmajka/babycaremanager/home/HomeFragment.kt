package com.mmajka.babycaremanager.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmajka.babycaremanager.MainActivity.Companion.CURRENT_ACTIVITY
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

        setupRecycler()

//        viewModel.actions.observe(viewLifecycleOwner, Observer { newActions ->
//            binding.activitiesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            binding.activitiesRv.adapter = ActivityAdapter(newActions)
//        })

        binding.inventoryRv.diaper.setOnClickListener {
            CURRENT_ACTIVITY = "Diaper"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(container, DiaperFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.bath.setOnClickListener{
            CURRENT_ACTIVITY = "Bath"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.feeding.setOnClickListener{
            CURRENT_ACTIVITY = "Feeding"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(container, FeedingFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.sleep.setOnClickListener{
            CURRENT_ACTIVITY = "Sleep"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }
        binding.inventoryRv.walk.setOnClickListener{
            CURRENT_ACTIVITY = "Walk"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(container, ActionFragment()).addToBackStack("").commit()
        }

        binding.lastCard.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(container, AllActions()).addToBackStack("").commit()
        }
        binding.activitiesRv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(container, AllActions()).addToBackStack("").commit()
        }
    }

    private fun setupRecycler(){
        viewModel.getCare()
        viewModel.actions.observe(viewLifecycleOwner, Observer {
            binding.activitiesRv.adapter = ActivityAdapter((it))
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            binding.activitiesRv.layoutManager = layoutManager
        })
    }

}