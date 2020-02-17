package com.tapan.architectureDemo

import com.tapan.architectureDemo.core.functional.Either
import com.tapan.architectureDemo.dictionary.data.ResultRepository
import com.tapan.architectureDemo.dictionary.model.Result
import com.tapan.architectureDemo.dictionary.model.TermsResponse
import com.tapan.architectureDemo.dictionary.usecases.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class GetResultTest : UnitTest() {

    private val termsResponse = TermsResponse(
        listOf(
            Result(author = "hello", word = "test", thumbsDown = 20, thumbsUp = 50),
            Result(author = "xyz", word = "test", thumbsDown = 20, thumbsUp = 40),
            Result(author = "alpha", word = "test", thumbsDown = 22, thumbsUp = 60),
            Result(author = "beta", word = "test", thumbsDown = 25, thumbsUp = 70)
        )
    )

    @Mock
    private lateinit var resultRepository: ResultRepository

    private lateinit var getResult: GetResult

    @Before
    fun setUp() {
        getResult = GetResult(resultRepository, TestCoroutineScope(), Dispatchers.IO)
        runBlocking {
            `when`(resultRepository.getResult("test"))
                .thenReturn(Either.Right(termsResponse.results))
        }

    }

    @Test
    fun `test loading data`() {
        runBlocking {
            resultRepository.getResult("test").either({

            }, {
                    assert(it.isNotEmpty())
            })
        }
    }
}