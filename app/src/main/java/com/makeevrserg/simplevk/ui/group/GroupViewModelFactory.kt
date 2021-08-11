package com.makeevrserg.simplevk.ui.group

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GroupViewModelFactory(
    private val friendID:String?,
    private val group: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
            return GroupViewModel(friendID,group, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}