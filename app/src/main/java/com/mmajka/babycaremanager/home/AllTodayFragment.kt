package com.mmajka.babycaremanager.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.databinding.AllTodayFragmentBinding
import com.mmajka.babycaremanager.utils.onClickListener
import kotlinx.android.synthetic.main.all_today_fragment.*
import kotlinx.android.synthetic.main.single_activity_full.*

class AllTodayFragment : Fragment(), onClickListener {

    private lateinit var viewModel: AllTodayViewModel
    private lateinit var binding: AllTodayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.all_today_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllTodayViewModel::class.java)

        setupRecycler()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
    }

    override fun onClick(position: Int, view: View, action: BasicActionEntity) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(position: Int, view: View, action: BasicActionEntity) {
        val dialog = AlertDialog.Builder(context).setTitle("Delete")
            .setMessage("Delete this action?")
            .setPositiveButton("Delete"){dialog, which ->
                val id = action.id
                viewModel.deleteAction(id, position)
                setupRecycler()
                today_recycler_view.adapter!!.notifyDataSetChanged()
                Log.i("Delete: ","${viewModel.actoday.value!!.size}")
            }
            .setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun setupRecycler(){
        viewModel.getToday()
        viewModel.actoday.observe(viewLifecycleOwner, Observer {
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            binding.todayRecyclerView.layoutManager = layoutManager
            binding.todayRecyclerView.adapter = FullActivityAdapter(it, this)
            Log.i("Size: ","${viewModel.actoday.value!!.size}")
        })
    }
}