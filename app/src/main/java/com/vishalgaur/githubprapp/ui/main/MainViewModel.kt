package com.vishalgaur.githubprapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishalgaur.githubprapp.network.GitHubApi
import com.vishalgaur.githubprapp.network.PullRequest
import kotlinx.coroutines.launch

enum class GitHubApiStatus { ERROR, DONE, LOADING }

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData<GitHubApiStatus>()
    val status: LiveData<GitHubApiStatus> get() = _status

    private val _pullRequests = MutableLiveData<List<PullRequest>>()
    val pullRequests: LiveData<List<PullRequest>> get() = _pullRequests

    private val _newPullRequests = MutableLiveData<List<PullRequest>>()
    val newPullRequests: LiveData<List<PullRequest>> get() = _newPullRequests

    private var _repoName: String? = null
    val repoName: String? get() = _repoName

    private var currPage = 1

    init {
        initRepoName()
        getAllClosedPRs()
    }

    private fun initRepoName() {
        viewModelScope.launch {
            try {
                _repoName = GitHubApi.repoName
            } catch(e: Exception) {
                Log.d(TAG, "exception = ${e.message}")
            }
        }
    }

    private fun getAllClosedPRs() {
        viewModelScope.launch {
            _status.value = GitHubApiStatus.LOADING
            try {
                _pullRequests.value = GitHubApi.retrofitService.getPullRequests()
                Log.d(TAG, "dataSize = ${pullRequests.value?.size}")
                _status.value = GitHubApiStatus.DONE
            } catch (e: Exception) {
                Log.d(TAG, "data = ${e.message}")
                _status.value = GitHubApiStatus.ERROR
                _pullRequests.value = listOf()
            }
        }
    }

    fun getMorePullRequests() {
        viewModelScope.launch {
            _status.value = GitHubApiStatus.LOADING
            try {
                val currData = _pullRequests.value?.toMutableList()
                _newPullRequests.value = GitHubApi.retrofitService.getPullRequests(page = (currPage+1).toString())
                currData?.addAll(_newPullRequests.value ?: emptyList())
                _pullRequests.value = currData ?: emptyList()
                _status.value = GitHubApiStatus.DONE
                currPage++
                Log.d(TAG, "dataSize = ${pullRequests.value?.size}, currPage = $currPage")
            } catch (e: Exception) {
                _status.value = GitHubApiStatus.ERROR
            }
        }
    }
}