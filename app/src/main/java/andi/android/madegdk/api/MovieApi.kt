package andi.android.madegdk.api

import andi.android.madegdk.response.MovieResponse
import andi.android.madegdk.response.TvSeriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/discover/movie")
    fun getMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/discover/tv")
    fun getTvSeries(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<TvSeriesResponse>
}