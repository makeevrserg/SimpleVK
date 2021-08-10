package com.makeevrserg.simplevk.ui.friends

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.makeevrserg.simplevk.event.EventHandler
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.friends.FriendsService
import com.vk.sdk.api.friends.dto.FriendsGetFieldsResponse
import com.vk.sdk.api.friends.dto.FriendsUserXtrLists
import com.vk.sdk.api.users.dto.UsersFields
import java.lang.NumberFormatException

class FriendsViewModel(
    val friendID: String?,
    application: Application
) : AndroidViewModel(application) {


    private val _friendItems = MutableLiveData<List<FriendsUserXtrLists>>()
    public val friendItems: LiveData<List<FriendsUserXtrLists>>
        get() = _friendItems

    private val _isLoading = MutableLiveData(true)
    public val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _errorEvent = MutableLiveData<EventHandler>()
    public val errorEvent: LiveData<EventHandler>
        get() = _errorEvent


    init {
        getFriends()
    }


    //Получения списка друзей пользователя или выбранного друга
    private fun getFriends() {
        VK.execute(
            FriendsService().friendsGet(
                userId = friendID?.toIntOrNull() ?: VK.getUserId(),
                fields = listOf(
                    UsersFields.FIRST_NAME_ABL,
                    UsersFields.LAST_NAME_ABL,
                    UsersFields.CITY,
                    UsersFields.STATUS,
                    UsersFields.PHOTO_200,
                    UsersFields.ONLINE
                )
            ), vkFriendCallback()
        )
    }


    private fun vkFriendCallback() = object : VKApiCallback<FriendsGetFieldsResponse> {
        override fun fail(error: Exception) {
            _isLoading.value = false
            _errorEvent.value = EventHandler("Ошибка загрузки друзей", EventHandler.EventType.ERROR)
        }

        override fun success(result: FriendsGetFieldsResponse) {
            _friendItems.value = result.items

            _isLoading.value = false
        }


    }
}