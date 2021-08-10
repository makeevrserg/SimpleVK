package com.makeevrserg.simplevk.ui.groups

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.makeevrserg.simplevk.event.EventHandler
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKApiManager
import com.vk.sdk.api.groups.GroupsService
import com.vk.sdk.api.groups.dto.*

class GroupsViewModel(
    val friendID: String?,
    application: Application
) : AndroidViewModel(application) {



    private val _groupItems = MutableLiveData<List<GroupsGroupFull>>()
    public val groupItems: LiveData<List<GroupsGroupFull>>
        get() = _groupItems

    private val _isLoading = MutableLiveData(true)
    public val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorEvent = MutableLiveData<EventHandler>()
    public val errorEvent: LiveData<EventHandler>
        get() = _errorEvent
    init {
        getGroups()
    }

    //Получаем список групп пользователя. Если указан friendID - получаем его список
    private fun getGroups() {
        VK.execute(
            GroupsService().groupsGetExtended(
                userId = friendID?.toIntOrNull() ?: VK.getUserId()
            ), vkGroupsCallback()
        )
    }

    private fun vkGroupsCallback() = object : VKApiCallback<GroupsGetExtendedResponse> {
        override fun fail(error: Exception) {
            _isLoading.value = false
            _errorEvent.value = EventHandler(
                "Ошибка загрузки групп: ${error.message}",
                EventHandler.EventType.ERROR
            )
        }

        override fun success(result: GroupsGetExtendedResponse) {
            _groupItems.value = result.items
            _isLoading.value = false
        }


    }
}