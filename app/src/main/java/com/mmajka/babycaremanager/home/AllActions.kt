package com.mmajka.babycaremanager.home

import android.app.AlertDialog
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
import com.mmajka.babycaremanager.MainActivity.Companion.CURRENT_ACTIVITY
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.actions.ActionFragment
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.databinding.AllActionsFragmentBinding
import com.mmajka.babycaremanager.diaper.DiaperFragment
import com.mmajka.babycaremanager.feeding.FeedingFragment
import com.mmajka.babycaremanager.utils.onClickListener
import kotlinx.android.synthetic.main.all_today_fragment.*

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
        val bundle = Bundle()
        val diaper = DiaperFragment()
        val feeding = FeedingFragment()
        val another = ActionFragment()

        bundle.putString("id", action.id)
        bundle.putString("title", action.title)
        bundle.putString("date", action.date)
        bundle.putString("time", action.time)
        bundle.putString("duration", action.duration)
        bundle.putString("info", action.info)

        when(action.title){
            "Diaper" ->{
                diaper.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, diaper).addToBackStack("").commit()
            }
            "Feeding" ->{
                feeding.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, feeding).addToBackStack("").commit()
            }
            "Bath" -> {
                CURRENT_ACTIVITY = "Bath"
                another.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, another).addToBackStack("").commit()
            }
            "Walk" ->{
                CURRENT_ACTIVITY = "Walk"
                another.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, another).addToBackStack("").commit()
            }
            "Sleep" ->{
                CURRENT_ACTIVITY = "Sleep"
                another.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, another).addToBackStack("").commit()
            }
        }

    }

    override fun onLongClick(position: Int, view: View, action: BasicActionEntity) {
        val dialog = AlertDialog.Builder(context).setTitle("Delete")
            .setMessage("Delete this action?")
            .setPositiveButton("Delete"){dialog, which ->
                val id = action.id
                viewModel.deleteAction(id, position)
                setupRecycler()
                today_recycler_view.adapter!!.notifyDataSetChanged()
                Log.i("Delete: ","${viewModel.actions.value!!.size}")
            }
            .setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
    }
}