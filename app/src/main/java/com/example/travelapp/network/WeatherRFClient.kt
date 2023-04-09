package com.example.travelapp.network

import com.example.travelapp.tools.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRFClient {

    private const val BASE_URL = "http://api.weatherapi.com/v1/"
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", Constants.WEATHER_API_KEY)
                        .build()
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retroifitService: WeatherApiService by lazy {
        retrofitBuilder.create(WeatherApiService::class.java)
    }

}