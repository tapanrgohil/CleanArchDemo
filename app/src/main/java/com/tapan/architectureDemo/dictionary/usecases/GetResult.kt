package com.tapan.architectureDemo.dictionary.usecases

import com.tapan.architectureDemo.dictionary.data.ResultRepository
import com.tapan.architectureDemo.dictionary.model.Result
import com.tapan.architectureDemo.core.exception.MyException
import com.tapan.architectureDemo.core.functional.Either
import com.tapan.architectureDemo.core.interactor.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Suggestion from api
 */
class GetResult constructor(
    private val repository: ResultRepository,
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher
) : UseCase<List<Result>, GetResult.Param>(scope, dispatcher) {

    override suspend fun run(params: Param): Either<MyException, List<Result>> {
        return repository.getResult(params.term)
    }

    data class Param(val term: String)
}