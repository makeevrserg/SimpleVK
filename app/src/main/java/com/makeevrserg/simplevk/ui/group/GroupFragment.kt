package com.makeevrserg.simplevk.ui.group

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.makeevrserg.simplevk.R
import com.makeevrserg.simplevk.databinding.GroupFragmentBinding
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.group_list_adapter.*

class GroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GroupFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.group_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val arguments = GroupFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = GroupViewModelFactory(arguments.group, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(GroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //Ошибки
        viewModel.errorEvent.observe(viewLifecycleOwner, {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                it.getContent() ?: return@observe,
                Snackbar.LENGTH_SHORT
            ).show()
        })


        return binding.root
    }
}