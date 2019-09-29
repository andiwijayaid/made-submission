package andi.android.popfav.api

import andi.android.popfav.model.MovieDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/{id}")
    fun getMovieDetail(
            @Path("id") id: Int?,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<MovieDetailResponse>
}