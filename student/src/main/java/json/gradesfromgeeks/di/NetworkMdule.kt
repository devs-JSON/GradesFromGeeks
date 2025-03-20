package json.gradesfromgeeks.di

import json.gradesfromgeeks.data.source.remote.service.HuggingFaceApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.apply
import kotlin.jvm.java

val networkModule = module {

    single<HuggingFaceApiService> {
        Retrofit.Builder()
            .baseUrl("https://api-inference.huggingface.co/")
            .client(createHuggingFaceClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HuggingFaceApiService::class.java)
    }
}
const val apiKey = "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"
private fun createHuggingFaceClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}