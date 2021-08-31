package com.proway.gitrepoapp.di

import android.content.Context
import com.proway.gitrepoapp.database.AppDatabase
import com.proway.gitrepoapp.database.dao.GithubDao
import com.proway.gitrepoapp.repository.GithubPullRequestRepository
import com.proway.gitrepoapp.repository.GithubRepository
import com.proway.gitrepoapp.services.GithubServices
import com.proway.gitrepoapp.services.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideGitHubRepository(dao: GithubDao, services: GithubServices): GithubRepository = GithubRepository(dao, services)

    @Provides
    fun providePullRequestRepository(dao: GithubDao, services: GithubServices): GithubPullRequestRepository = GithubPullRequestRepository(dao, services)

    @Provides
    fun providesGitHubServices(): GithubServices = RetrofitService.getGithubServices()

    @Provides
    fun providesGitHubDao(@ApplicationContext context: Context): GithubDao {
        return  AppDatabase.getDatabase(context).getGithubDao()
    }
}