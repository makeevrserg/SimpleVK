package com.makeevrserg.simplevk.ui.group

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.makeevrserg.simplevk.event.EventHandler
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.sdk.api.groups.GroupsService
import com.vk.sdk.api.groups.dto.*

class GroupViewModel(
    private val friendID:String?,
    private val groupString: String,
    application: Application
) : ViewModel() {
    private val TAG = "GroupViewModel"
    private val _isLoading = MutableLiveData(true)
    public val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _group = MutableLiveData<GroupsGroupFull>()
    public val group: LiveData<GroupsGroupFull>
        get() = _group
    private val _errorEvent = MutableLiveData<EventHandler>()
    public val errorEvent: LiveData<EventHandler>
        get() = _errorEvent

    init {
        loadGroupsID()
    }

    private fun loadGroupsID() {



        //groupsGetById не работает, поэтому пришлось делать так
        //Выбирается список групп пользователя
        //Находится позиция переданной id групы
        //Через offset берется первая группа
        VK.execute(
            GroupsService().groupsGet(
                userId=friendID?.toIntOrNull()?:VK.getUserId(),
            ), callback = vkApiEveryGroupCallback()
        )


    }
    //Тут загружается конкретная группа
    private fun loadGroup(offset:Int){
        VK.execute(
            GroupsService().groupsGetExtended(
                userId=friendID?.toIntOrNull()?:VK.getUserId(),
                offset=offset,
                count=1,
                fields = GroupsFields.values().asList()

            ), callback = vkGroupsCallback()
        )
    }

    private fun vkGroupsCallback() = object : VKApiCallback<GroupsGetExtendedResponse> {
        override fun fail(error: Exception) {
            _isLoading.value = false
            _errorEvent.value = EventHandler(
                "Ошибка загрузки группы: ${error.message}",
                EventHandler.EventType.ERROR
            )
        }

        override fun success(result: GroupsGetExtendedResponse) {
            _group.value = result.items[0]
            _isLoading.value = false
            Log.d(TAG, "success: ${_group.value?.isMember}")
            Log.d(TAG, "success: ${_group.value?.isMember?.value}")
        }


    }
    
    private fun vkApiEveryGroupCallback() = object : VKApiCallback<GroupsGetResponse> {
        override fun fail(error: Exception) {
            _errorEvent.value =
                EventHandler("Ошибка загрузки групы ${error.message}", EventHandler.EventType.ERROR)
        }

        override fun success(result: GroupsGetResponse) {
            val offset = result.items.indexOf(groupString.toIntOrNull())
            loadGroup(offset)
        }
    }


}