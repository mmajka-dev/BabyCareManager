package com.mmajka.babycaremanager.feeding

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mmajka.babycaremanager.R
import com.mmajka.babycaremanager.databinding.FeedingFragmentBinding

class FeedingFragment : Fragment() {

    /*Jeśli wybrana zostanie lewa albo prawa pierś to ujawnia się timer. Jeśli formula to przycisk play zmienia się w check,
    timer się ukrywa a pokazuje edit text z dopiskiem ml. Jeśli wybór pada na posiłek to oba są ukryte i zostaje
    tylko check oraz comment ET
     */

    private lateinit var bindig: FeedingFragmentBinding
    private lateinit var viewModel: FeedingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = DataBindingUtil.inflate(inflater, R.layout.feeding_fragment, container, false)
        return bindig.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}