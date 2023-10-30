package com.bahadori.codechallenge.core.network.retrofit

import com.bahadori.codechallenge.core.network.NextopNetworkDataSource
import com.bahadori.codechallenge.core.network.model.RatesDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNextopNetworkApi {
    @GET("code-challenge/index.php")
    suspend fun getRates(): RatesDto
}

@Singleton
class RetrofitNextopNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NextopNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RetrofitNextopNetworkApi::class.java)

    override suspend fun getRates(): RatesDto = networkApi.getRates()

    companion object {
        private const val BASE_URL = "https://lokomond.com/"
    }
}