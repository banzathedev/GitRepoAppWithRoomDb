package com.proway.gitrepoapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.gitrepoapp.repository.GithubRepository
import com.proway.gitrepoapp.repository.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class ListViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val repo = ReposRepository()

    private val _changes = MutableLiveData<Boolean>()
    var changes: LiveData<Boolean> = _changes

    private val _refresh = MutableLiveData<Boolean>()
    var refresh: LiveData<Boolean> = _refresh

    fun callGetRepoPrs(user: String, repoName: String) {
        repo.getPrsOfARepo(user, repoName) {
            _changes.value = it
        }
    }

    fun callRepoByLangs(lang: String) {

        repo.getReposBylang(lang) {
            _refresh.value = it
        }
    }
}
