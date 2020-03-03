package com.jason.codexgithubtest.helper

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(
    layoutManager: RecyclerView.LayoutManager,
    private var visibleThreshold: Int
) : RecyclerView.OnScrollListener() {
    var currentPage: Int = 1
    private var startingPageIndex = 1
    private var previousTotalItemCount = 0
    private var isLoading = true
    private var maxPage: Int = 1

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    init {
        when (layoutManager) {
            is LinearLayoutManager -> this.mLayoutManager = layoutManager
            is GridLayoutManager -> {
                this.mLayoutManager = layoutManager
                visibleThreshold *= layoutManager.spanCount
            }
        }
        println("visibleThreshold = $visibleThreshold, CurrentPage/MaxPage = $currentPage/$maxPage")
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is GridLayoutManager -> lastVisibleItemPosition =
                (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        /**
         * invalidate dataset, we should reset the recyclerview back to initial state (Rarely happen)
         * */
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) this.isLoading = true
        }

        /**
         * check to see if the dataset count has changed when still loading
         * */
        if (isLoading && totalItemCount > previousTotalItemCount + 1) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        /**
         * check to see if we have breached the visibleThreshold and need to reload more data.
         * */
        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount && currentPage < maxPage) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            isLoading = true
            println("CurrentPage/MaxPage = $currentPage/$maxPage")
        }
    }

    /**
     * call when performing new searches
     * */
    fun resetState(maxPage: Int) {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.isLoading = true
        this.maxPage = maxPage
    }

    /**
     * method to prevent onLoadMore triggered when getting new dataset
     * */
    fun isLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)
}