package com.uc3m.cypherbloc.models

import com.google.gson.annotations.SerializedName

data class XoN(
    @SerializedName("count")
    var anon: String,
    var char: String,
    var count: String,
    var wordlist: Int
)

data class XoNAux(
    @SerializedName("SearchPassAnon")
    var json: XoN
)
