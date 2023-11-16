package com.example.templateapplication.model.extraMateriaal

import androidx.annotation.DrawableRes
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.templateapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExtraItemViewModel : ViewModel() {
    private val _extraItemState = MutableStateFlow(ExtraItemState())
    val extraItemState = _extraItemState.asStateFlow()

    private val _listExtraItems = loadExtraItems().toMutableStateList()

    private var addedItems: MutableList<ExtraItemState> = mutableListOf()


    val listExtraItems: List<ExtraItemState>
        get() = _listExtraItems

    val title: String
        get() = extraItemState.value.title

    val category: String
        get()= extraItemState.value.category

    val price: Double
        get() = extraItemState.value.price

    val amount: Int
        get() = extraItemState.value.amount

    val imageResourceId: Int
        get() = extraItemState.value.imageResourceId

    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
        listExtraItems.find { it.extraItemId == item.extraItemId }?.let { extraItem ->
            extraItem.amount = amount
        }

    fun changeExtraItemEditing(item: ExtraItemState, editing: Boolean) =
        listExtraItems.find { it.extraItemId == item.extraItemId }?.let { extraItem ->
            extraItem.isEditing = editing
        }

    fun addItemToCart(item: ExtraItemState) {

        val existingItem = addedItems.find { it.extraItemId == item.extraItemId }

        if (existingItem != null) {
            existingItem.amount = item.amount
        } else {
            addedItems.add(item)
        }
    }

    fun removeItemFromCart(item: ExtraItemState) {
        val existingItem = addedItems.find { it.extraItemId == item.extraItemId }
        if (existingItem != null) {
            changeExtraItemAmount(existingItem, 0)
            addedItems.remove(existingItem)
        }

    }


    fun getListAddedItems() : List<ExtraItemState> {
        return addedItems
    }


    fun getListSorted(index: Int): List<ExtraItemState> {
        val sortedList = when (index) {
            0 ->  listExtraItems.sortedBy { it.price } // Sort asc
            1 ->  listExtraItems.sortedByDescending { it.price } // Sort desc
            2 ->  listExtraItems.sortedBy { it.title } // Sort by name asc
            3 ->  listExtraItems.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")


        }
        return sortedList

    }


    fun loadExtraItems(): List<ExtraItemState>{
        return listOf<ExtraItemState>(
            ExtraItemState(1,"Stoel", "stoel", 2.99, 0, R.drawable.foto7 ),
            ExtraItemState(2,"Tafel", "tafel", 2.99, 0, R.drawable.foto7 ),
            ExtraItemState(3,"Stoel2", "stoel", 2.80, 0, R.drawable.foto6 ),
            ExtraItemState(4,"Bank", "stoel", 10.0, 0, R.drawable.foto6 ),
            ExtraItemState(5,"Stoel", "stoel", 2.99, 0, R.drawable.foto7 ),
            ExtraItemState(6,"Tafel", "tafel", 2.99, 0, R.drawable.foto7 ),
            ExtraItemState(7,"Stoel2", "stoel", 2.80, 0, R.drawable.foto6 ),
            ExtraItemState(8,"Bank", "stoel", 10.0, 0, R.drawable.foto6 ),
        )
    }
}