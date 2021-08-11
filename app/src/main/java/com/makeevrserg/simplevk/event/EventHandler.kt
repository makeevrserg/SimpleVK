package com.makeevrserg.simplevk.event


class EventHandler (private val content: String,private val type:EventType) {
    enum class EventType{
        ERROR,FRIEND_FRIENDS,FRIEND_GROUPS
    }
    //При создании класса эвент считаетсся включенным.
    var isEnabled = true
        private set

    //Получаем контент во фрагменте, созданный в ViewModel
    //После вызова функии
    fun getContent(): String? {
        if (!isEnabled)
            return null
        isEnabled = false
        return content
    }

}