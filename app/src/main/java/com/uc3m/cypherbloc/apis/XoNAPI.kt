package com.uc3m.cypherbloc.apis

import com.uc3m.cypherbloc.models.XoNAux
import retrofit2.Response
import retrofit2.http.GET

interface XoNAPI {

    //hay que añadir los 10 primeros caracteres del hash de la pass al GET de abajo
    @GET("/api/v1/pass/anon/")
    suspend fun CheckPass(): Response<XoNAux>

}