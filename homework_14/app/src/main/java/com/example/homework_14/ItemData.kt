package com.example.homework_14

//created abstract class to represent common properties for different item types
abstract class ItemData {
    abstract val id: Int
    abstract val image: Int
    abstract val name: String
    abstract val description: String
}