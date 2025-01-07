package com.example.ahmednaeemfetch.network

import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItemRepository{

    override suspend fun getItems() : ApiResponse {
        return try {
            val data = apiService.getItems()
                // Validate API data to prevent crashes from invalid values
                .filter { !it.name.isNullOrBlank() && it.listId != null && it.id != null }
                // Sort the data first by id, then by name
                .sortedWith(compareBy<Item> { it.id }.thenBy { it.name })
                // Group items by their listId
                .groupBy { it.listId }

            ApiResponse(
                true,
                null,
                data.toSortedMap{key1: Int, key2: Int -> key1.compareTo(key2)}
            )
        } catch (e: Exception) {
            //Handle all exceptions
            ApiResponse(
                isSuccess = false,
                errorMessage = e.message,
                null
            )
        }
    }
}
