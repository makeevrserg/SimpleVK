package com.makeevrserg.simplevk.ui.friend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.makeevrserg.simplevk.R
import com.makeevrserg.simplevk.databinding.FriendFragmentBinding
import com.makeevrserg.simplevk.databinding.GroupFragmentBinding
import com.makeevrserg.simplevk.ui.group.GroupFragmentArgs
import com.makeevrserg.simplevk.ui.group.GroupViewModel
import com.makeevrserg.simplevk.ui.group.GroupViewModelFactory
import java.lang.IllegalArgumentException

class FriendFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FriendFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.friend_fragment,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(this.activity).application
        val arguments = FriendFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = FriendViewModelFactory(arguments.friendId, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FriendViewModel::class.java)
        binding.viewModel = viewModel


        //Ошибки
        viewModel.errorEvent.observe(viewLifecycleOwner, {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                it.getContent() ?: return@observe,
                Snackbar.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        })

        //Пользователь нажал на кнопку друзей друга
        viewModel.lookFriendsEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(
                FriendFragmentDirections.
                actionFriendFragmentToFriendsFragment().
                setUserId(it.getContent() ?: return@observe)
            )

        })

        //Пользователь нажал на кнопку групп друга
        viewModel.lookGroupEvent.observe(viewLifecycleOwner, {

            findNavController().navigate(
                FriendFragmentDirections.
                actionFriendFragmentToGroupsFragment().
                setUserId(it.getContent() ?: return@observe)
            )

        })
        return binding.root
    }


}