package com.proway.gitrepoapp.model

import com.google.gson.annotations.SerializedName

data class GitHubPullRequestResponse(
    @SerializedName("url")
    val urlOfPr: String,
    @SerializedName("id")
    val idOfThePr: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("number")
    val numberOfThePr: Int,
    @SerializedName("state")
    val stateOfThePr: String,
    @SerializedName("title")
    val titleOfThePr: String,
    @SerializedName("user")
    val userOfThePr: UserPfThePr,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,

    )

data class UserPfThePr(
    @SerializedName("login")
    val loginOfUserOfPr: String,
    @SerializedName("id")
    val idOfUserOfPr: Int,
    @SerializedName("avatar_url")
    val avatarUserPr: String,
    @SerializedName("url")
    val urlOfUserOfThePr: String,

    )

