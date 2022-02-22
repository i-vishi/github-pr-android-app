package com.vishalgaur.githubprapp.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.github.com/repos/"
private const val USER_NAME = "octocat"
private const val REPO_NAME = "hello-world"

private val gson = GsonBuilder().create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface GitHubApiService {

    @GET("$USER_NAME/$REPO_NAME/pulls?status=closed")
    suspend fun getPullRequests(): List<PullRequest>

}

object GitHubApi {
    val retrofitService: GitHubApiService by lazy { retrofit.create(GitHubApiService::class.java) }
}