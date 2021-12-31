package com.imandroid.simplefoursquare.data.network.mock

import android.content.Context
import android.content.res.AssetManager
import android.util.SparseArray
import com.imandroid.simplefoursquare.SimpleFourSquare
import com.imandroid.simplefoursquare.util.extension.readAssetsFile
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.lang.reflect.Type

class CallAdapterFactoryWithMockUp(var mockUpInfoMap: SparseArray<MockUpInfo>) :
    CallAdapter.Factory() {
    companion object {
        fun create(mockUpInfoMap: SparseArray<MockUpInfo>): CallAdapterFactoryWithMockUp {
            return CallAdapterFactoryWithMockUp(mockUpInfoMap)
        }
    }
    var rxJava2CallAdapterFactory: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val callAdapter = rxJava2CallAdapterFactory.get(returnType, annotations, retrofit)
        var mockUpInfo: MockUpInfo? = null
        for (annotation in annotations) {
            if (MOCKUP::class.java == annotation.annotationClass.java) {
                mockUpInfo = MockUpInfo()
                mockUpInfo.responseCode = Integer.valueOf((annotation as MOCKUP).value[0])
                val mockResponse = SimpleFourSquare.getAppContext()!!.assets.readAssetsFile("mock/${annotation.value[1]}")
                Timber.i("mockResponse=>$mockResponse")
                mockUpInfo.mockResponse = mockResponse
            }
        }
        return CallAdapterFactoryWrapperWithMockUp(callAdapter!!, mockUpInfo, mockUpInfoMap)
    }
}