package andi.android.madegdk.api

import andi.android.madegdk.response.MovieDetailResponse
import andi.android.madegdk.response.MovieResponse
import andi.android.madegdk.response.TvSeriesDetailResponse
import andi.android.madegdk.response.TvSeriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/discover/movie")
    fun getMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/search/movie")
    fun searchMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("query") query: String?
    ): Call<MovieResponse>

    @GET("3/discover/tv")
    fun getTvSeries(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<TvSeriesResponse>

    @GET("3/search/tv")
    fun searchTvSeries(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("query") query: String?
    ): Call<TvSeriesResponse>

    @GET("3/movie/{id}")
    fun getMovieDetail(
            @Path("id") id: Int?,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<MovieDetailResponse>

    @GET("3/tv/{id}")
    fun getTvSeriesDetail(
            @Path("id") id: Int?,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<TvSeriesDetailResponse>

    @GET("3/discover/movie")
    fun getTodayReleasedMovie(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("primary_release_date.gte") releaseDateGte: String,
            @Query("primary_release_date.ite") releaseDateIte: String
    ): Call<MovieResponse>
}