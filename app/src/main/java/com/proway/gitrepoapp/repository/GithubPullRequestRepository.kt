package com.proway.gitrepoapp.repository

import com.proway.gitrepoapp.database.dao.GithubDao
import com.proway.gitrepoapp.services.GithubServices
import javax.inject.Inject

class GithubPullRequestRepository @Inject constructor(
    private val githubDao: GithubDao,
    private val githubServices: GithubServices,
) {
}