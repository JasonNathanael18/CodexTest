package com.jason.codexgithubtest.view

import com.jason.codexgithubtest.model.StoryResponse

interface MainListener {
    fun onItemClick(story: StoryResponse)
}
