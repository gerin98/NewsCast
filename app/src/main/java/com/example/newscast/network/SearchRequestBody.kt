package com.example.newscast.network

class SearchRequestBody(
    val action: String = "getArticles",
    val keyword: String = "nba",
    val articlesPage: Int = 1,
    val articlesCount: Int = 30,
    val articlesSortBy: String = "date",
    val articlesSortByAsc: Boolean = false,
    val articlesArticleBodyLen: Int = -1,
    val resultType: String = "articles",
    val includeArticleImage: Boolean = true,
    val dataType: Array<String> = arrayOf("news"),
    val apiKey: String = "63eed15f-9dd0-41c9-b511-ed6efaea22fb",
    val lang: String = "eng"
) {}
