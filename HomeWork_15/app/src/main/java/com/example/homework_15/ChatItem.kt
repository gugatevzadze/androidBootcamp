package com.example.homework_15

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ChatItem(
    @Json(name = "id") val id: Int,
    @Json(name = "image") val image: String?,
    @Json(name = "owner") val owner: String,
    @Json(name = "last_message") val lastMessage: String,
    @Json(name = "last_active") val lastActive: String,
    @Json(name = "unread_messages") val unreadMessages: Int,
    @Json(name = "is_typing") val isTyping: Boolean,
    @Json(name = "laste_message_type") val lastMessageType: ItemType
) {
    //enum class for the message types
    enum class ItemType {
        @Json(name = "text") TEXT,
        @Json(name = "file") FILE,
        @Json(name = "voice") VOICE
    }
}
