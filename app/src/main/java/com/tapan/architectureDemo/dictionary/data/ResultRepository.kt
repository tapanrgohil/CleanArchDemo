package com.tapan.architectureDemo.dictionary.data

import com.tapan.architectureDemo.dictionary.model.Result
import com.tapan.architectureDemo.core.exception.MyException
import com.tapan.architectureDemo.core.functional.Either

interface ResultRepository {
    suspend fun getResult(term: String): Either<MyException, List<Result>>
}