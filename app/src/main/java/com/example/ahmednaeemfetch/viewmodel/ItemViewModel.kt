package com.example.ahmednaeemfetch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahmednaeemfetch.network.Item
import com.example.ahmednaeemfetch.network.ItemRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepo: ItemRepositoryImpl
): ViewModel() {

    init {
        // Trigger initial data loading when the ViewModel is created.
        getItems()
    }

    // LiveData to expose the fetched items grouped by their listId.
    private val _items = MutableLiveData<Map<Int, List<Item>>>()
    val items: LiveData<Map<Int, List<Item>>> get() = _items

    // LiveData to expose any error messages encountered.
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getItems() = viewModelScope.launch {
        val result = itemRepo.getItems()
        if (result.isSuccess) {
            _items.postValue(result.data!!)
        } else {
            _error.postValue(result.errorMessage!!)
        }
    }
}