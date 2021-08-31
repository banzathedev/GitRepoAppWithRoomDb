package com.proway.gitrepoapp.services


import com.proway.gitrepoapp.model.GithubModel
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query

interface ServiceRepoByLanguage {
    @GET("search/repositories")
    fun getByLang(@Query("q") lang: String): Call<GithubModel>
}