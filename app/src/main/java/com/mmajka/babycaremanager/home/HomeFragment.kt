package com.mmajka.babycaremanager.home


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mmajka.babycaremanager.MainActivity.Companion.CURRENT_ACTIVITY
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.actions.ActionFragment
import com.mmajka.babycaremanager.data.BasicActionEntity
import com.mmajka.babycaremanager.databinding.HomeFragmentBinding
import com.mmajka.babycaremanager.diaper.DiaperFragment
import com.mmajka.babycaremanager.feeding.FeedingFragment
import com.mmajka.babycaremanager.invite.InviteFragment
import com.mmajka.babycaremanager.utils.onClickListener
import kotlinx.android.synthetic.main.home_fragment.*

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
        val container = R.id.fragment_container
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getChild(binding.babyName, binding.age)
        Glide.with(this).load(viewModel.getPhotoPath()).error(R.drawable.ic_boy).into(photo)
        Log.i("PATH", "${viewModel.getPhotoPath()}")
        setupActivitiesRecycler()
        setupTodayRecycler()

        binding.inventoryRv.diaper.setOnClickListener {
            CURRENT_ACTIVITY = "Diaper"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                DiaperFragment()
            ).addToBackStack("").commit()
        }
        binding.inventoryRv.bath.setOnClickListener{
            CURRENT_ACTIVITY = "Bath"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                ActionFragment()
            ).addToBackStack("").commit()
        }
        binding.inventoryRv.feeding.setOnClickListener{
            CURRENT_ACTIVITY = "Feeding"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                FeedingFragment()
            ).addToBackStack("").commit()
        }
        binding.inventoryRv.sleep.setOnClickListener{
            CURRENT_ACTIVITY = "Sleep"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                ActionFragment()
            ).addToBackStack("").commit()
        }
        binding.inventoryRv.walk.setOnClickListener{
            CURRENT_ACTIVITY = "Walk"
            Toast.makeText(context, "$CURRENT_ACTIVITY", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                ActionFragment()
            ).addToBackStack("").commit()
        }

        binding.tdFullscreen.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                AllTodayFragment()
            ).addToBackStack("").commit()
        }
        binding.acFullscreen.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                AllActions()
            ).addToBackStack("").commit()
        }

        binding.invite.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                container,
                InviteFragment()
            ).addToBackStack("").commit()
        }
    }

    private fun setupActivitiesRecycler(){
        viewModel.getCare()
        viewModel.actions.observe(viewLifecycleOwner, Observer {
            binding.activitiesRv.adapter = ActivityAdapter((it))
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            binding.activitiesRv.layoutManager = layoutManager
        })
    }

    private fun setupTodayRecycler(){
        viewModel.getToday()
        viewModel.actoday.observe(viewLifecycleOwner, Observer {
            binding.summaryRv.adapter = ActivityAdapter((it))
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            binding.summaryRv.layoutManager = layoutManager
        })
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = activity!!.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
        return uri.path
    }



}