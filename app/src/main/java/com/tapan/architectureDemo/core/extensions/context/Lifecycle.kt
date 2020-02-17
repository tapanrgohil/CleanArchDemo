package com.tapan.architectureDemo.core.extensions.context

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tapan.architectureDemo.core.exception.MyException


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<MyException>> LifecycleOwner.failure(liveData: L, body: (MyException?) -> Unit) =
    liveData.observe(this, Observer(body))