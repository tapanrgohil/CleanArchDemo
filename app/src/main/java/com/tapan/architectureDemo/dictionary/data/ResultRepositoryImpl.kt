package com.tapan.architectureDemo.dictionary.data

import com.tapan.architectureDemo.core.data.BaseRepository
import com.tapan.architectureDemo.dictionary.model.Result
import com.tapan.architectureDemo.core.exception.MyException
import com.tapan.architectureDemo.core.functional.Either
import com.tapan.architectureDemo.core.platform.NetworkHandler
import com.tapan.architectureDemo.dictionary.data.datasource.LocalDataSource
import com.tapan.architectureDemo.dictionary.data.datasource.RemoteDataSource

class ResultRepositoryImpl
    (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseRepository(), ResultRepository {

    override suspend fun getResult(term: String): Either<MyException, List<Result>> {
        return either {
            //Error handling
            val local = getLocalList(term)
            return@either if (local.isNotEmpty()) {
                local
            } else {
                if (networkHandler.isConnected) getApiList(term)
                    .also { insertResultToDatabase(it) }
                else throw MyException.NetworkError(RuntimeException("No offline data available!"))
            }
        }

    }


    private suspend fun insertResultToDatabase(it: List<Result>) {
        localDataSource.addDictionaryEntity(it.map { result -> result.toDictionaryEntity() })
    }

    private suspend fun getApiList(term: String) = remoteDataSource.getTerms(term).results

    private suspend fun getLocalList(term: String) = localDataSource.getDictionaryEntity(term)
        .map { it.toResult() }
}




