package com.proway.gitrepoapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.gitrepoapp.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {


    private val _changes = MutableLiveData<Boolean>()
    var changes: LiveData<Boolean> = _changes

    fun LoadModels() {
        repository.getLangs(){
            _changes.value = it
        }

    }


}