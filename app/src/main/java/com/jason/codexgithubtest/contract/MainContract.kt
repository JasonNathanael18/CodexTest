package com.jason.codexgithubtest.contract

import com.jason.codexgithubtest.model.StoryResponse

interface MainContract {
    interface View {
        suspend fun loadTopStoryList(listTop: ArrayList<Int>)
        fun setListData(topStoryList: MutableList<StoryResponse>)
        fun updateProgressbarResult(progress: Int)
    }

    interface Presenter {
        fun getTopStories()
        suspend fun getStoryDetailFirstTime(listTop: ArrayList<Int>)
    }
}