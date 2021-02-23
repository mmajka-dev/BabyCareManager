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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
import com.mmajka.babycaremanager.settings.SettingsFragment
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
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getChild(binding.babyName, binding.age)
        Glide.with(this).load(viewModel.getPhotoPath()).error(R.drawable.ic_boy).into(photo)
        Log.i("PATH", "${viewModel.getPhotoPath()}")
        setupActivitiesRecycler()

        binding.inventoryRv.diaper.setOnClickListener {
            CURRENT_ACTIVITY = "Diaper"
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, DiaperFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }
        binding.inventoryRv.bath.setOnClickListener{
            CURRENT_ACTIVITY = "Bath"
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, ActionFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }
        binding.inventoryRv.feeding.setOnClickListener{
            CURRENT_ACTIVITY = "Feeding"
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, FeedingFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }
        binding.inventoryRv.sleep.setOnClickListener{
            CURRENT_ACTIVITY = "Sleep"
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, ActionFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }
        binding.inventoryRv.walk.setOnClickListener{
            CURRENT_ACTIVITY = "Walk"
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, ActionFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }

        binding.acFullscreen.setOnClickListener {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, AllActions(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        }

        binding.invite.setOnClickListener {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.fragment_container, SettingsFragment(), "h")
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
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