package com.imandroid.simplefoursquare.data.network.mock

import android.util.SparseArray
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CallAdapterFactoryWrapperWithMockUp<B : Any?, R : Any?>(private var callAdapter: CallAdapter<R, B>, private var mockUpInfo: MockUpInfo?, private var mockUpInfoMap: SparseArray<MockUpInfo>) :
    CallAdapter<R, B> {
    override fun responseType(): Type {
        return callAdapter.responseType()
    }

    override fun adapt(call: Call<R>): B {
        val request = call.request()
        mockUpInfoMap.put((request.url().toString() + request.method()).hashCode(), mockUpInfo)
        return callAdapter.adapt(call)
    }
}