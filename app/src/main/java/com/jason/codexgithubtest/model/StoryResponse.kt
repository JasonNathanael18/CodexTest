package com.jason.codexgithubtest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryResponse
    (
    @SerializedName("title") val title: String = "",
    @SerializedName("by") val by: String = "",
    @SerializedName("time") val time: Int = 0,
    @SerializedName("descendants") val totalComment: Int = 0,
    @SerializedName("score") val score: Int = 0,
    @SerializedName("id") val id: Int = 0
) : Parcelable


