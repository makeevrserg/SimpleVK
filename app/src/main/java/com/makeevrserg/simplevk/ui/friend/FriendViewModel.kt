package com.makeevrserg.simplevk.ui.friend

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makeevrserg.simplevk.event.EventHandler
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersFields
import com.vk.sdk.api.users.dto.UsersUserXtrCounters

class FriendViewModel(
    val friendID: String,
    application: Application
) : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    public val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _lookFriendsEvent = MutableLiveData<EventHandler>()
    public val lookFriendsEvent: LiveData<EventHandler>
        get() = _lookFriendsEvent

    private val _lookGroupEvent = MutableLiveData<EventHandler>()
    public val lookGroupEvent: LiveData<EventHandler>
        get() = _lookGroupEvent

    private val _errorEvent = MutableLiveData<EventHandler>()
    public val errorEvent: LiveData<EventHandler>
        get() = _errorEvent
    private val _friend = MutableLiveData<UsersUserXtrCounters>()
    public val friend: LiveData<UsersUserXtrCounters>
        get() = _friend


    init {
        loadFriend()
    }

    //Загрузка информации о друге
    private fun loadFriend() {
        VK.execute(
            UsersService().usersGet(
                userIds = listOf(friendID),
                fields = UsersFields.values().asList()
            ), callback = vkApiCallback()
        )
    }


    public fun onFriendsSelected(){
        _lookFriendsEvent.value = EventHandler(friendID,EventHandler.EventType.FRIEND_FRIENDS)
    }
    public fun onGroupSelected(){
        _lookGroupEvent.value = EventHandler(friendID,EventHandler.EventType.FRIEND_GROUPS)
    }
    private fun vkApiCallback() = object : VKApiCallback<List<UsersUserXtrCounters>> {

        override fun fail(error: Exception) {
            _isLoading.value = false
            _errorEvent.value =
                EventHandler("Ошибка загрузки друга", EventHandler.EventType.ERROR)
        }

        override fun success(result: List<UsersUserXtrCounters>) {
            _isLoading.value = false
            _friend.value = result[0]
        }
    }


}