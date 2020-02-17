package com.tapan.architectureDemo.dictionary.data.datasource

import com.tapan.architectureDemo.dictionary.model.TermsResponse
import retrofit2.http.*

interface DictionaryApiService {
    @Headers(
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: 243e96d12amsh61085e4f6968b78p150863jsnda973eae933b"
    )
    @GET("define")
    suspend fun getTerms(@Query("term") term: String): TermsResponse
}