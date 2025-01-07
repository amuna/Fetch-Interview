package com.example.ahmednaeemfetch.di

import com.example.ahmednaeemfetch.network.ApiService
import com.example.ahmednaeemfetch.network.ItemRepository
import com.example.ahmednaeemfetch.network.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    /**
     * Provides a singleton instance of the [ApiService] interface.
     *
     * @return An instance of [ApiService] configured for API communication.
     */
    @Provides
    @Singleton
    fun providerServiceApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(generateConverterFactory())
            .build().
            create(ApiService::class.java)
    }

    /**
     * Provides a singleton instance of the [ItemRepository] interface.
     *
     * @param apiService An instance of [ApiService] to fetch data from the API.
     * @return An instance of [ItemRepositoryImpl] to handle data operations.
     */
    @Provides
    @Singleton
    fun provideItemRepo(
        apiService: ApiService
    ): ItemRepository {
        return ItemRepositoryImpl(apiService)
    }

    /**
     * Generates a Gson converter factory for JSON serialization and deserialization.
     *
     * @return A [GsonConverterFactory] instance for JSON serialization and deserialization.
     */
    private fun generateConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}