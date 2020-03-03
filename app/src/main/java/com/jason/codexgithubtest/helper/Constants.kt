package com.jason.codexgithubtest.helper

import com.jason.codexgithubtest.BuildConfig

class Constants {
    object ApiEndPoint {
        const val BASE_URL = BuildConfig.BASE_URL
        const val TOP_STORIES = "/v0/topstories.json"
    }
}