package com.example.homework_14

//overriding the common properties
data class ItemDataTwo(
    override val id: Int,
    override val image: Int,
    override val name: String,
    override val description: String
): ItemData()