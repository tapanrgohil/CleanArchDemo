package com.tapan.architectureDemo.dictionary.model


import com.google.gson.annotations.SerializedName

data class DictionaryRS(
    @SerializedName("list")
    var list: List<SuggestionsRS> = listOf()
)