package com.example.templateapplication.model.extraMateriaal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.R
import com.example.templateapplication.api.ExtraMateriaalApplication
import com.example.templateapplication.data.ExtraMateriaalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ExtraItemViewModel(private val extraMateriaalRepository : ExtraMateriaalRepository) : ViewModel() {
    private val _extraItemState = MutableStateFlow(ExtraItemState())
    val extraItemState = _extraItemState.asStateFlow()

    private val _extraItemListState = MutableStateFlow(ExtraItemListState())
    val extraItemListState = _extraItemListState.asStateFlow()

    var extraMateriaalApiState: ExtraMateriaalApiState by mutableStateOf(ExtraMateriaalApiState.Loading)
        private set

    init {
        getApiExtraMateriaal()
    }

    //veranderen



    private var addedItems = listOf<ExtraItemState>().toMutableStateList()



    fun changeExtraItemAmount(item: ExtraItemState, amount: Int) =
       extraItemListState.value.currentExtraMateriaalList.find { it.extraItemId == item.extraItemId }?.let { extraItem ->
            extraItem.amount = amount
        }
    fun getTotalPrice() : Double{
        return addedItems.sumOf { it.price * it.amount }
    }
    fun changeExtraItemEditing(item: ExtraItemState, editing: Boolean) =
        _extraItemListState.value.currentExtraMateriaalList.find { it.extraItemId == item.extraItemId }?.let { extraItem ->
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

    private fun getApiExtraMateriaal(){
        viewModelScope.launch {
            try{
                //use the repository
                //val tasksRepository = ApiTasksRepository() //repo is now injected
                val listResult = extraMateriaalRepository.getExtraMateriaal()
                _extraItemListState.update {
                    it.copy(currentExtraMateriaalList = listResult)
                }
                extraMateriaalApiState = ExtraMateriaalApiState.Success(listResult)
            }
            catch (e: IOException){
                val errorMessage = e.message ?: "An error occurred"

                // Set the error state with the error message
                extraMateriaalApiState = ExtraMateriaalApiState.Error(errorMessage)
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ExtraMateriaalApplication)
                val extraMateriaalRepository = application.container.extraMateriaalRepository
                ExtraItemViewModel(extraMateriaalRepository = extraMateriaalRepository
                )
            }
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
            0 ->   _extraItemListState.value.currentExtraMateriaalList.sortedBy { it.price } // Sort asc
            1 ->   _extraItemListState.value.currentExtraMateriaalList.sortedByDescending { it.price } // Sort desc
            2 ->   _extraItemListState.value.currentExtraMateriaalList.sortedBy { it.title } // Sort by name asc
            3 ->   _extraItemListState.value.currentExtraMateriaalList.sortedByDescending { it.title } // Sort by name desc
            else -> throw IllegalArgumentException("Invalid index: $index")


        }
        return sortedList

    }


}