package com.example.ahmednaeemfetch.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

/**
 * @param [isSuccess] if API call eas successful (true) or failed (false).
 * @param [errorMessage] Contains an error message if the operation failed
 * (null if the operation was successful).
 * @param [data] Represents valid grouped data from api call. (null if the operation failed)
 */
data class ApiResponse(
    val isSuccess: Boolean,
    val errorMessage: String?,
    val data: Map<Int, List<Item>>?
)

/**
 * Represents an individual item retrieved from the API.
 *
 * @property id The unique identifier for the item.
 * @property listId The identifier for the list to which the item belongs.
 * @property name The name of the item.
 */
@Serializable
data class Item(
    @SerialName("id") val id: Int?,
    @SerialName("listId") val listId: Int?,
    @SerialName("name") val name: String?,
)

/**
 * Service interface for interacting with the API to fetch data.
 *
 * Provides a method to retrieve a list of items from the specified endpoint.
 */
interface ApiService {

    /**
     * Fetches a list of items from the server.
     *
     * @return A [List] of [Item] objects representing the data fetched from the API.
     */
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}