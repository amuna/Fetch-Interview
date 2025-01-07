package com.example.ahmednaeemfetch.network

interface ItemRepository {

    /**
     * Fetches and validates a list of [Item] form API
     *
     * @return [ApiResponse] with successful data or failed error message
     */
    suspend fun getItems() : ApiResponse
}