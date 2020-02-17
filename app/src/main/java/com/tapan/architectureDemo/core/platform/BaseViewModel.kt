package com.tapan.architectureDemo.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapan.architectureDemo.core.exception.MyException
import com.tapan.architectureDemo.core.functional.Either
import kotlinx.coroutines.*
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    val progressLiveData by lazy { MutableLiveData<Boolean>() }
    val errorLiveData by lazy { MutableLiveData<Exception>() }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


//    protected val loginContract by inject<LoginContract>()
//    protected val serviceContract by inject<ServiceProductContract>()


    fun <R> postData(observer: MutableLiveData<R>, result: suspend () -> Either<MyException, R>) {
        progressLiveData.postValue(false)
        viewModelScope.launch {
            progressLiveData.postValue(true)
            result.invoke().either({
                progressLiveData.postValue(false)
                errorLiveData.postValue(it)
            }, {
                progressLiveData.postValue(false)
                observer.postValue(it)
            })
        }


    }

    fun <R> postNullData(
        observer: MutableLiveData<R>,
        result: suspend () -> Either<MyException, R>
    ) {
        progressLiveData.postValue(false)
        viewModelScope.launch {
            progressLiveData.postValue(true)
            result.invoke().either({
                progressLiveData.postValue(false)
                errorLiveData.postValue(it)
                observer.postValue(null)
            }, {
                progressLiveData.postValue(false)
                observer.postValue(it)
            })
        }


    }



    override fun onCleared() {
        viewModelJob.cancel()
        uiScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}