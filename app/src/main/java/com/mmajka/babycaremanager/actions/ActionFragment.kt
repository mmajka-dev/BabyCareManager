package com.mmajka.babycaremanager.actions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.MaterialToolbar
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.ActionFragmentBinding

class ActionFragment : Fragment() {

    companion object {
        fun newInstance() = ActionFragment()
    }

    private lateinit var viewModel: ActionViewModel
    private lateinit var binding: ActionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.action_fragment, container, false)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}