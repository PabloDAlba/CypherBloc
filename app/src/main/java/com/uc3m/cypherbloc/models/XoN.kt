package com.uc3m.cypherbloc.models

import com.google.gson.annotations.SerializedName

data class XoN(
    @SerializedName("anon") var anon: String,
    @SerializedName("char") var char: String,
    @SerializedName("count") var count: String,
    @SerializedName("wordlist") var wordlist: String
)

data class XoNAux(
    @SerializedName("SearchPassAnon")  var json: XoN
)
