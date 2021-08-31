package com.proway.gitrepoapp.repository


import com.proway.gitrepoapp.BuildConfig

import com.proway.gitrepoapp.model.LanguagesResponse
import com.proway.gitrepoapp.model.RepoPullRequestResponse

import com.proway.gitrepoapp.services.*
import com.proway.gitrepoapp.singletons.SingletonLangs
import com.proway.gitrepoapp.singletons.SingletonRepoPrs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReposRepository {

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
                            getLangs(){
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

    fun getPrsOfARepo(user: String, repoName: String, callback: (Boolean) -> Unit) {
        val api = RetrofitBuilder.getInstance(BuildConfig.GITHUB_API_URL)
            .create(ServicePRSOfARepo::class.java)
        api.getPRS(user, repoName).clone()
            .enqueue(object : Callback<List<RepoPullRequestResponse>> {
                override fun onResponse(
                    call: Call<List<RepoPullRequestResponse>>,
                    response: Response<List<RepoPullRequestResponse>>
                ) {
                    if (response.body() != null) {
                        SingletonRepoPrs.resp = response.body()
                        callback(true)
                    } else {
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<List<RepoPullRequestResponse>>, t: Throwable) {
                }

            })
    }


}