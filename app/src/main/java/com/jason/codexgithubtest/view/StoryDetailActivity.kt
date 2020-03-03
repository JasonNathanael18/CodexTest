package com.jason.codexgithubtest.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jason.codexgithubtest.R
import com.jason.codexgithubtest.model.StoryResponse
import kotlinx.android.synthetic.main.activity_story_detail.*
import java.text.SimpleDateFormat
import java.util.*

class StoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_STORY = "extra_story"
    }
    private lateinit var storyDetail: StoryResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        supportActionBar?.title = resources.getString(R.string.actionbar_detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        getBundle()
        setClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getBundle(){
        storyDetail = intent.getParcelableExtra(EXTRA_STORY) as StoryResponse
        setData(storyDetail)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(storyDetail: StoryResponse){
        detail_title.text = storyDetail.title
        detail_username.text = this.getString(R.string.by, storyDetail.by)
        val date = Date(storyDetail.time.toLong())
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        detail_date.text = simpleDateFormat.format(date).toString()
    }

    private fun setClickListener(){
        favourite.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("favourite",storyDetail.title)
            setResult( Activity.RESULT_OK,returnIntent)
            finish()
        }
    }
}
