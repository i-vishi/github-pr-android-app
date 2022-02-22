package com.vishalgaur.githubprapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.github.com/repos/"
private const val USER_NAME = "i-vishi"
private const val REPO_NAME = "shopping-android-app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GitHubApiService {

    @GET("$USER_NAME/$REPO_NAME/pulls?state=closed")
    suspend fun getPullRequests() : List<String>

}

object GitHubApi {
    val retrofitService : GitHubApiService by lazy { retrofit.create(GitHubApiService::class.java) }
}