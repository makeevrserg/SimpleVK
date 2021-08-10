package com.makeevrserg.simplevk.ui.friend

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FriendViewModelFactory(
    private val friend: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            return FriendViewModel(friend, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}