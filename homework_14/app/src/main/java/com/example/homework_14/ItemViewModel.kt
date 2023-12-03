package com.example.homework_14

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

//viewmodel for managing a list of ItemData using coroutines and flows
class ItemViewModel : ViewModel() {

    private val _itemFlow = MutableStateFlow<List<ItemData>>(emptyList())
    val itemFlow: SharedFlow<List<ItemData>> = _itemFlow.asStateFlow()

    //function to add new items to the list, i differentiated the data classes using item types from enum class
    fun addNewItem(itemType: ItemType) {
        viewModelScope.launch {
            _itemFlow.value = _itemFlow.value.toMutableList().also {
                when (itemType) {
                    ItemType.ITEM_ONE -> {
                        it.add(
                            ItemDataOne(
                                id = Random.nextInt(),
                                image = R.drawable.ben_affleck_smoking_1,
                                name = "Item One",
                                description = "Description for Item One"
                            )
                        )
                    }
                    ItemType.ITEM_TWO -> {
                        it.add(
                            ItemDataTwo(
                                id = Random.nextInt(),
                                image = R.drawable.ben_affleck_smoking_2,
                                name = "Item Two",
                                description = "Description for Item Two"
                            )
                        )
                    }
                }
            }
        }
    }
    //refresh functionality, i empty the list everytime the recycler is refreshed
    fun refreshItems() {
        viewModelScope.launch {
            _itemFlow.value = emptyList()
        }
    }
    //removing the item from the list
    fun removeItem(item: ItemData) {
        viewModelScope.launch {
            _itemFlow.value = _itemFlow.value.toMutableList().also {
                it.remove(item)
            }
        }
    }
    //enum class for representing different item types
    enum class ItemType {
        ITEM_ONE,
        ITEM_TWO
    }
}