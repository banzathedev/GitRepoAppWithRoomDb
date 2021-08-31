package com.proway.gitrepoapp.services


import com.proway.gitrepoapp.model.GitHubPullRequestResponse
import com.proway.gitrepoapp.model.GithubModel
import com.proway.gitrepoapp.model.GithubRepositoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServices {

    /**
     * Usamos o @Query para deixar os paremetros dinamicos de acordo com a documentacao da API
     */
    @GET("/search/repositories")
    fun fetchRepositories(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Call<GithubRepositoryResponse>

    @GET("/repos/{userP}/{repoName}/pulls")
    fun getPullRequests(
        @Path("userP") user: String,
        @Path("repoName") repoName: String
    ): Call<GitHubPullRequestResponse>

    @GET("search/repositories")
    fun getRepositoriesByLang(@Query("q") lang: String): Call<GithubModel>


}