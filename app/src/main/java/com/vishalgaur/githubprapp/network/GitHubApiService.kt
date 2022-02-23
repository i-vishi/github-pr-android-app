package com.vishalgaur.githubprapp.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/repos/"
private const val USER_NAME = "JetBrains"
private const val REPO_NAME = "kotlin"

private val gson = GsonBuilder().create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface GitHubApiService {

    @GET("$USER_NAME/$REPO_NAME/pulls")
    suspend fun getPullRequests(
        @Query("status") status: String = "closed",
        @Query("page") page: String = "1"
    ): List<PullRequest>

}

object GitHubApi {
    val retrofitService: GitHubApiService by lazy { retrofit.create(GitHubApiService::class.java) }
    val repoName = "$USER_NAME/$REPO_NAME"
}