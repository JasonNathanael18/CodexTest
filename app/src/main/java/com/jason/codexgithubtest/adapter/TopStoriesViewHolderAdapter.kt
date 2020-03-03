package com.jason.codexgithubtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.codexgithubtest.R
import com.jason.codexgithubtest.model.StoryResponse
import com.jason.codexgithubtest.view.MainListener
import com.jason.codexgithubtest.view.StoryDetailActivity
import kotlinx.android.synthetic.main.item_top_story.view.*

class TopStoriesViewHolderAdapter (private var mainListener: MainListener
) : RecyclerView.Adapter<TopStoriesViewHolderAdapter.StoryViewHolder>() {

    private val storyList = ArrayList<StoryResponse>()

    fun setData(items: ArrayList<StoryResponse>) {
        storyList.clear()
        storyList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int = storyList.size


    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.setView(storyList[position])
        holder.setClickListener(storyList[position])
    }

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setView(story: StoryResponse) {
            with(itemView) {
                item_story_title.text = story.title
                item_story_score.text = context.getString(R.string.score, story.score.toString())
                item_story_comment.text = context.getString(R.string.comment, story.totalComment.toString())
            }
        }

        fun setClickListener(story: StoryResponse){
            itemView.setOnClickListener {
                //val intentToDetail = Intent(itemView.context, StoryDetailActivity::class.java)
                //intentToDetail.putExtra(StoryDetailActivity.EXTRA_STORY, story)
                //itemView.context.startActivity(intentToDetail)
                mainListener.onItemClick(story)

            }
        }
    }
}