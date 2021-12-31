package com.imandroid.simplefoursquare.data.network.mock

import android.util.SparseArray
import com.imandroid.simplefoursquare.BuildConfig
import okhttp3.*
import java.io.IOException

class MockupInterceptor(private val mockUpInfoMap: SparseArray<MockUpInfo>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var response: Response
        val request = chain.request()
        if (BuildConfig.MOCKUP && mockUpInfoMap[(request.url().toString() + request.method()).hashCode()] != null) {
            val mockUpInfo: MockUpInfo? = mockUpInfoMap[(request.url().toString() + request.method()).hashCode()]
            if (mockUpInfo != null) {
                response = Response.Builder()
                    .code(mockUpInfo.responseCode)
                    .message(mockUpInfo.mockResponse!!)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), mockUpInfo.mockResponse!!.toByteArray()))
                    .addHeader("content-type", "application/json")
                    .build()
            }
        } else
            response = chain.proceed(request)

        return response
    }
}