package com.alex.yakushev.app.torrentslistvisualizer.service

import com.alex.yakushev.app.torrentslistvisualizer.model.GeneralMoviesData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Oleksandr on 10-Sep-17.
 */
interface YtsApi {
    @get:GET("/api/v2/list_movies.json")
    val listOfMovies: Call<GeneralMoviesData>
}