package com.makeevrserg.simplevk.ui.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.makeevrserg.simplevk.R
import com.makeevrserg.simplevk.databinding.FriendsFragmentBinding
import com.makeevrserg.simplevk.databinding.GroupFragmentBinding
import com.makeevrserg.simplevk.ui.adapters.FriendsAdapter
import com.makeevrserg.simplevk.ui.adapters.FriendsListener
import com.makeevrserg.simplevk.ui.adapters.GroupListener
import com.makeevrserg.simplevk.ui.adapters.GroupsAdapter
import com.makeevrserg.simplevk.ui.friend.FriendFragmentArgs
import com.makeevrserg.simplevk.ui.group.GroupFragmentArgs
import com.makeevrserg.simplevk.ui.group.GroupViewModel
import com.makeevrserg.simplevk.ui.group.GroupViewModelFactory
import com.makeevrserg.simplevk.ui.groups.GroupsFragmentDirections

class FriendsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FriendsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.friends_fragment,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(this.activity).application
        val arguments = FriendsFragmentArgs.fromBundle(arguments ?: Bundle())
        val viewModelFactory = FriendsViewModelFactory(arguments.userId, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FriendsViewModel::class.java)
        binding.viewModel = viewModel

        //Ошибки
        viewModel.errorEvent.observe(viewLifecycleOwner, {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                it.getContent() ?: return@observe,
                Snackbar.LENGTH_SHORT
            ).show()
        })

        //RecyclerView
        val adapter = FriendsAdapter(FriendsListener { friend, view ->
            this.findNavController()
                .navigate(
                    FriendsFragmentDirections.actionFriendsFragmentToFriendFragment(
                        friend.id.toString()
                    )
                )
        })

        binding.recyclerView.adapter = adapter
        viewModel.friendItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }


}