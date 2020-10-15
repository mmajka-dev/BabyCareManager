package com.mmajka.babycaremanager.invite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmajka.babycaremanager.R

class InviteFragment : Fragment() {

    companion object {
        fun newInstance() = InviteFragment()
    }

    private lateinit var viewModel: InviteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.invite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InviteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}