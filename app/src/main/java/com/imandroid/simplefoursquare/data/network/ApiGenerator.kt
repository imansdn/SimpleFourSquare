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


import android.util.SparseArray
import com.imandroid.simplefoursquare.data.network.mock.CallAdapterFactoryWithMockUp
import com.imandroid.simplefoursquare.data.network.mock.MockUpInfo
import com.imandroid.simplefoursquare.data.network.mock.MockupInterceptor
import com.imandroid.simplefoursquare.util.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiGenerator {

    private const val BASE_URL = "https://api.foursquare.com/"
    private const val API_VERSION = "v3/"


    private const val CONNECT_TIME_OUT = 20L
    private const val READ_TIME_OUT = 60L
    private const val WRITE_TIME_OUT = 60L


    private var httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val headerInterceptor = HeaderInterceptor()
    private val mockUpInfoMap = SparseArray<MockUpInfo>()
    private var client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(MockupInterceptor(mockUpInfoMap))
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL+API_VERSION)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CallAdapterFactoryWithMockUp.create(mockUpInfoMap))
        .build()


    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    class HeaderInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", API_KEY)
                    .build()
            )
        }
    }



}



