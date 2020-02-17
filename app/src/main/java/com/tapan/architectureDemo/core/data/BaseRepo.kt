package com.tapan.architectureDemo.core.data

import com.tapan.architectureDemo.core.exception.MyException
import com.tapan.architectureDemo.core.functional.Either

open class BaseRepository {
    suspend fun <R> either(
        data: suspend () -> R
    ): Either<MyException, R> {
        return try {
            Either.Right(
                data.invoke()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Either.Left(MyException.NetworkError(RuntimeException("Network issues")))
        }

    }
}