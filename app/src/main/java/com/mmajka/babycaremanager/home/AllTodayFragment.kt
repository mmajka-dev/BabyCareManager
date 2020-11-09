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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.actions.ActionFragment
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.databinding.AllTodayFragmentBinding
import com.mmajka.babycaremanager.diaper.DiaperFragment
import com.mmajka.babycaremanager.feeding.FeedingFragment
import com.mmajka.babycaremanager.invite.InviteFragment
import com.mmajka.babycaremanager.utils.onClickListener
import kotlinx.android.synthetic.main.activity_main.*
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
        val container = R.id.fragment_container
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
        val bundle = Bundle()
        val diaper = DiaperFragment()
        val feeding = FeedingFragment()
        val another = ActionFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        bundle.putString("id", action.id)
        bundle.putString("title", action.title)
        bundle.putString("date", action.date)
        bundle.putString("time", action.time)
        bundle.putString("duration", action.duration)
        bundle.putString("info", action.info)

        when(action.title){
            "Diaper" ->{
                diaper.arguments = bundle
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                fragmentTransaction.replace(R.id.fragment_container, diaper, "h")
                fragmentTransaction.addToBackStack("")
                fragmentTransaction.commit()
            }
            "Feeding" ->{
                feeding.arguments = bundle
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                fragmentTransaction.replace(R.id.fragment_container, feeding, "h")
                fragmentTransaction.addToBackStack("")
                fragmentTransaction.commit()
            }
            else -> {
                another.arguments = bundle
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                fragmentTransaction.replace(R.id.fragment_container, another, "h")
                fragmentTransaction.addToBackStack("")
                fragmentTransaction.commit()
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