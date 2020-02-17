package com.tapan.architectureDemo.dictionary.model


import com.google.gson.annotations.SerializedName

data class TermsResponse(
    @SerializedName("list")
    var results: List<Result> = listOf()
)