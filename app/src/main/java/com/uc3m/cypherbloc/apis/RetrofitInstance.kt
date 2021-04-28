package com.uc3m.cypherbloc.apis

import com.uc3m.cypherbloc.utils.Constants
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

        // lazy read-only property
        private val retrofit by lazy{
            val httpBuilder = OkHttpClient.Builder()

            val certificatePinner = CertificatePinner.Builder()
                .add("https://xposedornot.com/api/v1/pass/anon/")
                .build()

            val okHttpClient = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build()

             Retrofit.Builder()
                .baseUrl(Constants.XoNAPI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

    val api: XoNAPI by lazy {
        retrofit.create(XoNAPI::class.java)
    }

}