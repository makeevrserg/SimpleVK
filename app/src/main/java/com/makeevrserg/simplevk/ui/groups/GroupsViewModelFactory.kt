package com.makeevrserg.simplevk.ui.groups

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GroupsViewModelFactory(
    private val friendID: String?,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupsViewModel::class.java)) {
            return GroupsViewModel(friendID, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}