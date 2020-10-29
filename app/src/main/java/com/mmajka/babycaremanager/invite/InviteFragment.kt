package com.mmajka.babycaremanager.invite

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.InviteFragmentBinding

class InviteFragment : Fragment() {

    companion object {
        fun newInstance() = InviteFragment()
    }

    private lateinit var viewModel: InviteViewModel
    private lateinit var binding: InviteFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.invite_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InviteViewModel::class.java)
        getCode()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.shareFab.setOnClickListener {
            startActivity(getShareIntent(binding.code))
        }
    }

    private fun getCode(){
        viewModel.getCode().observe(viewLifecycleOwner, Observer { newCode ->
            binding.code.text = newCode
        })
    }

    private fun getShareIntent(view: TextView): Intent {
        return viewModel.getShareIntent(view)
    }
}