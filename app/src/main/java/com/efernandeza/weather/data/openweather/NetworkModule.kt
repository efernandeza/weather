package com.efernandeza.weather.data.openweather

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun converterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun okHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(AuthInterceptor())

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun openWeatherRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun openWeatherService(
        retrofit: Retrofit
    ): OpenWeatherService {
        return retrofit.create(OpenWeatherService::class.java)
    }
}
