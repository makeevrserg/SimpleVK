package com.makeevrserg.simplevk.ui.groups

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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.makeevrserg.simplevk.R
import com.makeevrserg.simplevk.databinding.GroupsFragmentBinding
import com.makeevrserg.simplevk.ui.adapters.GroupListener
import com.makeevrserg.simplevk.ui.adapters.GroupsAdapter
import com.makeevrserg.simplevk.ui.friends.FriendsFragmentArgs
import com.makeevrserg.simplevk.ui.friends.FriendsViewModel
import com.makeevrserg.simplevk.ui.friends.FriendsViewModelFactory

class GroupsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: GroupsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.groups_fragment,
            container,
            false
        )

        //ViewModel
        val application = requireNotNull(this.activity).application
        val arguments = FriendsFragmentArgs.fromBundle(arguments?:Bundle())
        val viewModelFactory = GroupsViewModelFactory(arguments.userId, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(GroupsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
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
        val adapter = GroupsAdapter(GroupListener { group, view ->
            //Надо было передать просто id группы, но groupsGetById не работает, поэтому пришлось передавать так
            this.findNavController()
                .navigate(
                    GroupsFragmentDirections.actionGroupsFragmentToGroupFragment(group.id.toString()).setUserID(arguments.userId)
                )
        })
        binding.recyclerView.adapter = adapter
        viewModel.groupItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

}