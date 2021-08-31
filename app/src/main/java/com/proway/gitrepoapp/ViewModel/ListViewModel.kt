package com.proway.gitrepoapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.gitrepoapp.model.GithubModel
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

    private val _repositories = MutableLiveData<List<GithubModel>>()
    val repositories: LiveData<List<GithubModel>> = _repositories

    /**
     * Criado esta variavel para armazenar a paginação que o user esta atuando.
     */
    private val _page = MutableLiveData(0)
    val page: LiveData<Int> = _page

    /**
     * Sempre será chamado passando a página, caso não passe nenhuma irá passar por default 1
     */
    fun fetchRepositories(language: String, page: Int = 1) {
        repository.fetchRepositories(language = language, page = page) { response, _ ->
            response?.let { resp ->
                _repositories.value = resp.items
            }
        }
    }

    /**
     * Atualiza a página
     */
    fun nextPage() {
        _page.value = _page.value!! + 1
    }


}



