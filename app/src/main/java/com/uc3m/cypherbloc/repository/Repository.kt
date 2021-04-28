package com.uc3m.cypherbloc.repository

import com.uc3m.cypherbloc.apis.RetrofitInstance
import com.uc3m.cypherbloc.models.XoNAux
import retrofit2.Response

class Repository {

    suspend fun getCheck(hashKey: String): Response<XoNAux> {
        return RetrofitInstance.api.CheckPass("$hashKey")

    }
}