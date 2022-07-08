package com.geektech.pixabay_kotlin.retrofit

import com.geektech.pixabay_kotlin.model.PhotoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/")
    fun getPhotos(
        @Query("key") key: String = "27743463-1ffe7881f218a91ea3cfc831d",
        @Query("q") namePhoto: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): Call<PhotoData>
}