package com.makeevrserg.simplevk.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.makeevrserg.simplevk.shared.Preferences
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope


class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val preferences = Preferences(application)

    public val VK_PERMISSIONS =
        arrayListOf(VKScope.FRIENDS, VKScope.GROUPS, VKScope.STATS, VKScope.STATUS)

    private val _authorized = MutableLiveData(true)
    public val authorized: LiveData<Boolean>
        get() = _authorized

    private fun checkForAuth() {
        //Проверка валидности токена
        VK.addTokenExpiredHandler(tokenTracker)

        //Необходимо проверить _authorized чтобы авторизация не открылась несколько раз
        if (preferences.getToken() == null && _authorized.value == true)
            _authorized.value = false
    }

    //Если токен просрочен - пользователю необходимо будет снова авторизоваться
    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            preferences.clearToken()
            if (_authorized.value == true)
                _authorized.value = false
        }
    }

    init {
        checkForAuth()
    }

    //Сохраняем токен при успешной авторизации
    public fun vkAuthCallback() = object : VKAuthCallback {
        override fun onLogin(token: VKAccessToken) {
            preferences.saveToken(token.accessToken)
        }

        override fun onLoginFailed(errorCode: Int) {
            return
        }
    }
}