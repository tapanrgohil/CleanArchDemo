package com.tapan.architectureDemo.dictionary.model


import com.google.gson.annotations.SerializedName

data class SuggestionsRS(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("current_vote")
    var currentVote: String = "",
    @SerializedName("defid")
    var defid: Int = 0,
    @SerializedName("definition")
    var definition: String = "",
    @SerializedName("example")
    var example: String = "",
    @SerializedName("permalink")
    var permalink: String = "",
    @SerializedName("sound_urls")
    var soundUrls: List<String> = listOf(),
    @SerializedName("thumbs_down")
    var thumbsDown: Int = 0,
    @SerializedName("thumbs_up")
    var thumbsUp: Int = 0,
    @SerializedName("word")
    var word: String = "",
    @SerializedName("written_on")
    var writtenOn: String = ""
)