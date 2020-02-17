package com.tapan.architectureDemo.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tapan.architectureDemo.core.functional.map
import com.tapan.architectureDemo.core.platform.BaseViewModel
import com.tapan.architectureDemo.dictionary.model.ResultView
import com.tapan.architectureDemo.dictionary.usecases.GetResult
import org.koin.core.inject

class DictionaryViewModel constructor() : BaseViewModel() {

    val getResult by inject<GetResult>()

    private var resultLiveData: MutableLiveData<List<ResultView>> = MutableLiveData()

    var ascending = true


    /**
     * Get data from api using use case.
     */
    fun getSuggestions(term: String) {
        postData(resultLiveData) {
            getResult.run(GetResult.Param(term))
                .map {
                    if (ascending) {
                        it.sortedByDescending { resultView -> resultView.thumbsUp }
                            .map { it.toResultView() }
                    } else {
                        it.sortedByDescending { resultView -> resultView.thumbsDown }
                            .map { it.toResultView() }
                    }
                }
        }

    }

    fun getSortedLastData(isThumbsUp: Boolean): MutableList<ResultView> {
        this.ascending = isThumbsUp
        return if (this.ascending) {
            resultLiveData.value.orEmpty().sortedByDescending { it.thumbsUp }.toMutableList()
        } else {
            resultLiveData.value.orEmpty().sortedByDescending { it.thumbsDown }.toMutableList()
        }
    }


    fun getResultLiveData() = (resultLiveData as LiveData<List<ResultView>>)
}