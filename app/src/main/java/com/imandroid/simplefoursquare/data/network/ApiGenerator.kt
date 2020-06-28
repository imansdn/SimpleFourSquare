/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.imandroid.simplefoursquare.data.network


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiGenerator {

    private const val BASE_URL = "https://api.foursquare.com/"
    private const val API_ENDPOINT = "v2/venues/"


    private const val CONNECT_TIME_OUT = 20L
    private const val READ_TIME_OUT = 60L
    private const val WRITE_TIME_OUT = 60L


    private var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var client = OkHttpClient.Builder().addInterceptor(interceptor)
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL+API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()


    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        if (authToken != null) {
            addRequestHeaders(authToken)
        }

        return retrofit.create(serviceClass)
    }

    private fun addRequestHeaders(authToken: String?) {
        client.interceptors().add(Interceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder().header("Authorization", authToken!!)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        })
    }
}



