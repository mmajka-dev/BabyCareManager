package com.mmajka.babycaremanager.diaper

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmajka.babycaremanager.R

class DiaperFragment : Fragment() {

    companion object {
        fun newInstance() = DiaperFragment()
    }

    private lateinit var viewModel: DiaperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diaper_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiaperViewModel::class.java)
        // TODO: Use the ViewModel
    }

}