package com.example.homework_15

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //suspend function to get chat messages using the GET HTTP method
    @GET("744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getChatMessages(): Response<List<ChatItem>>
}