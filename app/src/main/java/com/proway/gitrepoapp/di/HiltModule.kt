package com.proway.gitrepoapp.di

import com.proway.gitrepoapp.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideGitHubRepository(): GithubRepository = GithubRepository()
}