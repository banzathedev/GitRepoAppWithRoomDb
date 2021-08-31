package com.proway.gitrepoapp.repository

import com.proway.gitrepoapp.BuildConfig
import com.proway.gitrepoapp.database.dao.GithubDao
import com.proway.gitrepoapp.model.GithubRepositoryResponse
import com.proway.gitrepoapp.model.LanguagesResponse
import com.proway.gitrepoapp.services.GithubServices
import com.proway.gitrepoapp.services.Langs
import com.proway.gitrepoapp.services.RetrofitBuilder
import com.proway.gitrepoapp.services.RetrofitService
import com.proway.gitrepoapp.singletons.SingletonLangs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubDao: GithubDao,
    private val githubServices: GithubServices,
) {

    /**
     * Buscamos nossa Interface implementada do retrofit
     */


    /**
     * Vamos expor o serviço de fetchRepositories para as outras camadas.
     */
    fun fetchRepositories(
        language: String,
        sort: String = "stars",
        page: Int = 1,
        onComplete: (GithubRepositoryResponse?, String?) -> Unit
    ) {
        val call = githubServices.fetchRepositories(
            language = "language:$language",
            sort = sort,
            page = page
        )
        call.enqueue(object : Callback<GithubRepositoryResponse> {
            override fun onResponse(
                call: Call<GithubRepositoryResponse>,
                response: Response<GithubRepositoryResponse>
            ) {
                if (response.body() != null) {
                    onComplete(response.body(), null)
                } else {
                    onComplete(null, "Erro diferente")
                }
            }

            override fun onFailure(call: Call<GithubRepositoryResponse>, t: Throwable) {
                onComplete(null, t.message)
            }
        })

    }
    fun getLangs(callback: (Boolean) -> Unit) {
        RetrofitBuilder.getInstance(BuildConfig.GITHUB_LANGS_URL).create(Langs::class.java)
            .getLangs().clone().enqueue(object : Callback<List<LanguagesResponse>> {
                override fun onResponse(
                    call: Call<List<LanguagesResponse>>,
                    response: Response<List<LanguagesResponse>>
                ) {
                    response.body().let { resp ->
                        if (resp != null) {
                            SingletonLangs.resp = resp
                            callback(true)
                        } else {
                            getLangs() {
                                callback(false)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<LanguagesResponse>>, t: Throwable) {
                    println(t.message)
                }

            })
    }

}