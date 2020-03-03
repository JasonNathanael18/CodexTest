package com.jason.codexgithubtest.connection

import com.jason.codexgithubtest.helper.Constants
import com.jason.codexgithubtest.model.StoryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APICollections {
    @GET(Constants.ApiEndPoint.TOP_STORIES)
    fun getTopStory(): Deferred<Response<ArrayList<Int>>>

    @GET("/v0/item/{id}.json")
    fun getStory(@Path("id") id: String): Deferred<Response<StoryResponse>>
}