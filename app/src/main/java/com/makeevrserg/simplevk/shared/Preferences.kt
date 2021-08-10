package com.makeevrserg.simplevk.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.makeevrserg.simplevk.R
import java.lang.Exception

class Preferences(val application: Application) {
    private fun getSharedPref(): SharedPreferences? {
        return try {
            application.getSharedPreferences(
                application.getString(R.string.preferences),
                Context.MODE_PRIVATE
            )
        } catch (e: Exception) {
            return null
        }
    }

    fun saveToken( token: String) {
        val sharedPref = getSharedPref() ?: return
        with(sharedPref.edit()) {
            putString(application.getString(R.string.token), token)
            apply()
        }
    }

    fun clearToken(){
        val sharedPref = getSharedPref() ?: return
        with(sharedPref.edit()) {
            remove(application.getString(R.string.token))
            apply()
        }
    }
    fun getToken(): String? {
        val sharedPref = getSharedPref() ?: return null
        return sharedPref.getString(application.getString(R.string.token), null)

    }

}