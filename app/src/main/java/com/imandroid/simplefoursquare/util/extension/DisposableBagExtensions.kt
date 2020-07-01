package com.imandroid.simplefoursquare.util.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.disposedBy(bag: CompositeDisposable) {
    bag.add(this)
}