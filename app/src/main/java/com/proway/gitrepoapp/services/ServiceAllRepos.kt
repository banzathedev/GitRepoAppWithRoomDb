package com.proway.gitrepoapp.services


import retrofit2.Call
import retrofit2.http.GET

interface ServiceAllRepos {
    @GET("search/repositories?q=language:Java&sort=fork&order=desc")
    fun getRepos(): Call<GithubServices>

}