package com.tapan.architectureDemo

import com.tapan.architectureDemo.core.di.dataSourceModule
import com.tapan.architectureDemo.core.di.networkModule
import com.tapan.architectureDemo.core.di.useCaseModule
import com.tapan.architectureDemo.core.functional.Either
import com.tapan.architectureDemo.dictionary.DictionaryNetworkService
import com.tapan.architectureDemo.dictionary.DictionaryRepository
import com.tapan.architectureDemo.dictionary.model.DictionaryRS
import com.tapan.architectureDemo.dictionary.model.SuggestionsRS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelTest : KoinTest {

    @Mock
    lateinit var dictionaryRepository: DictionaryRepository

    val dictionaryNetworkService: DictionaryNetworkService by inject()

    val dictionaryRS = DictionaryRS(
        listOf(
            SuggestionsRS(author = "abc", word = "test", thumbsDown = 2, thumbsUp = 50),
            SuggestionsRS(author = "xyz", word = "test", thumbsDown = 20, thumbsUp = 40),
            SuggestionsRS(author = "qwert", word = "test", thumbsDown = 22, thumbsUp = 60),
            SuggestionsRS(author = "asdf", word = "test", thumbsDown = 25, thumbsUp = 70)
        )
    )

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            useCaseModule
            modules(listOf(useCaseModule, networkModule, dataSourceModule))
        }

        declareMock<DictionaryNetworkService> {
            given(getTerms("test")).willReturn(
                Single.just(
                    dictionaryRS
                )
            )
        }
    }

    /**
     * Network call test
     */
    @Test
    fun `test get suggestion from network call`() {
        dictionaryNetworkService.getTerms("test")
            .test()
            .assertNoErrors()
            .assertValue {
                it.list.isNotEmpty()
            }
        stopKoin()
    }


    /**
     * Use case call test from view model
     */
    @Test
    fun `test use case for getSuggestion`() {

        runBlocking {
            Mockito.`when`( (dictionaryRepository.getSuggestions("test")))
                .thenReturn((Either.Right(dictionaryRS.list)))

            val result = GetSuggestions(
                dictionaryRepository, CoroutineScope(TestCoroutineContext("test")),
                Dispatchers.Unconfined
            ).run(GetSuggestions.Param("test"))
            assert(result.isRight)

        }
//
    }
}
