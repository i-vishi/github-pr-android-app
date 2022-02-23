package com.vishalgaur.githubprapp.network

import com.squareup.moshi.Json

data class PullRequest(
    @Json(name = "base")
    val base: HashMap<String, Any?>,
    @Json(name = "body")
    val body: String?,
    @Json(name = "closed_at")
    val closed_at: String,
    @Json(name = "created_at")
    val created_at: String,
    @Json(name = "head")
    val head: HashMap<String, Any?>,
    @Json(name = "html_url")
    val html_url: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "issue_url")
    val issue_url: String,
    @Json(name = "merged_at")
    val merged_at: String,
    @Json(name = "node_id")
    val node_id: String,
    @Json(name = "number")
    val number: Int,
    @Json(name = "state")
    val state: String,
    @Json(name = "title")
    val title: String?,
    @Json(name = "updated_at")
    val updated_at: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "user")
    val user: HashMap<String, Any?>
)