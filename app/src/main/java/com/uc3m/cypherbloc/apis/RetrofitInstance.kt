package com.uc3m.cypherbloc.apis


import com.uc3m.cypherbloc.utils.Constants.Companion.PWNED_URL
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // lazy read-only property
    private val retrofit by lazy {
        val httpBuilder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder()
            .add("blockchain.info", "sha256/Z87j23nY+/WSTtsgE/O4ZcDVhevBohFPgPMU6rV2iSw=")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        Retrofit.Builder()
            .baseUrl(PWNED_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: PwnedAPI by lazy {
        retrofit.create(PwnedAPI::class.java)
    }

}