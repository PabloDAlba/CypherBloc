package com.uc3m.cypherbloc.apis

import com.uc3m.cypherbloc.models.XoNAux
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface XoNAPI {

    @GET
    suspend fun CheckPass(@Url url: String): Response<XoNAux>


}