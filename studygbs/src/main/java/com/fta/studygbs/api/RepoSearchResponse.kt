package com.fta.studygbs.api

import com.fta.studygbs.vo.Repo
import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Repo>
){
    var nextPage: Int? = null
}