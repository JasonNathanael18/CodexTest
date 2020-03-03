package com.jason.codexgithubtest.presenter

import com.jason.codexgithubtest.connection.RetrofitService
import com.jason.codexgithubtest.contract.MainContract
import com.jason.codexgithubtest.model.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter(private var MainView: MainContract.View) : MainContract.Presenter {

    private var topStoryList: MutableList<StoryResponse> = mutableListOf()
    private var count = 0

    override fun getTopStories() {
        GlobalScope.launch(Dispatchers.IO) {
            val postRequest = RetrofitService().api().getTopStory()
            val response = postRequest.await()
            if (response.isSuccessful) {
                MainView.loadTopStoryList(response.body() as ArrayList<Int>)
            }
        }
    }

    override suspend fun getStoryDetailFirstTime(listTop: ArrayList<Int>) {
        fetchStoryDetail(listTop)
    }

    private suspend fun fetchStoryDetail(listTop: ArrayList<Int>) {
        for (i in 0 until 10) {
            GlobalScope.async {
                val postRequest = RetrofitService().api().getStory(listTop[i].toString())
                val response = postRequest.await()
                if (response.isSuccessful) {
                    topStoryList.add(response.body() as StoryResponse)
                    count++
                    GlobalScope.launch(Dispatchers.Main) {
                        MainView.updateProgressbarResult(count)
                    }
                }
                if (count == 10) {
                    GlobalScope.launch(Dispatchers.Main) {
                        MainView.setListData(topStoryList)
                    }
                }
            }
        }
    }
}