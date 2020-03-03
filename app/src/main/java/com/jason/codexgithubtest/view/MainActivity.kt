package com.jason.codexgithubtest.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jason.codexgithubtest.R
import com.jason.codexgithubtest.adapter.TopStoriesViewHolderAdapter
import com.jason.codexgithubtest.contract.MainContract
import com.jason.codexgithubtest.model.StoryResponse
import com.jason.codexgithubtest.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, MainListener {

    private var mainPresenter = MainPresenter(this)
    private lateinit var adapter: TopStoriesViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.actionbar_main_title)
        initRecyclerView()
        mainPresenter.getTopStories()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (data != null) {
                title_last_clicked.text = data.getStringExtra("favourite")
            }
        }
    }

    override suspend fun loadTopStoryList(listTop: ArrayList<Int>) {
        mainPresenter.getStoryDetailFirstTime(listTop)
    }

    override fun setListData(topStoryList: MutableList<StoryResponse>) {
        adapter.setData(topStoryList as ArrayList<StoryResponse>)
    }

    override fun updateProgressbarResult(progress: Int) {
        progressBar.progress = progress
        if (progress == 10) progressBar.visibility = View.INVISIBLE
    }

    private fun initRecyclerView() {
        adapter = TopStoriesViewHolderAdapter(this)
        adapter.notifyDataSetChanged()
        rv_top_story.layoutManager = GridLayoutManager(this, 2)
        rv_top_story.adapter = adapter
    }

    override fun onItemClick(story: StoryResponse) {
        val intentToDetail = Intent(this, StoryDetailActivity::class.java)
        intentToDetail.putExtra(StoryDetailActivity.EXTRA_STORY, story)
        startActivityForResult(intentToDetail,1)
    }
}
