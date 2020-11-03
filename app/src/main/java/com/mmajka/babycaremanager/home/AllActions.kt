package com.mmajka.babycaremanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.databinding.AllActionsFragmentBinding
import com.mmajka.babycaremanager.utils.onClickListener

class AllActions : Fragment(), onClickListener {

    private lateinit var viewModel: AllActionsViewModel
    private lateinit var binding: AllActionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.all_actions_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllActionsViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        setupRecycler()
    }

    private fun setupRecycler(){
        viewModel.getCare()
        viewModel.actions.observe(viewLifecycleOwner, Observer {
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            binding.allRecyclerView.layoutManager = layoutManager
            binding.allRecyclerView.adapter = FullActivityAdapter(it, this)
        })
    }

    override fun onClick(position: Int, view: View, action: BasicActionEntity) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(position: Int, view: View, action: BasicActionEntity) {
        TODO("Not yet implemented")
    }
}