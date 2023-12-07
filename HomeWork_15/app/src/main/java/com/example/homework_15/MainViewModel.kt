package com.example.homework_15

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<ChatItem>>(emptyList())
    val chatMessages: StateFlow<List<ChatItem>> get() = _chatMessages.asStateFlow()
    private val apiService = RetrofitClient.create(ApiService::class.java)

    //function to fetch chat messages from the API using coroutines
    fun fetchChatMessages() {
        viewModelScope.launch {
            //network request to get chat messages using the API service
            val response = apiService.getChatMessages()

            //if the response is successful update the chatMessages using the received messages
            if (response.isSuccessful) {
                updateChatMessages(response.body() ?: emptyList())
            }
        }
    }
    //function to update the chatMessages StateFlow
    private fun updateChatMessages(messages: List<ChatItem>) {
        viewModelScope.launch {
            _chatMessages.emit(messages)
        }
    }
}