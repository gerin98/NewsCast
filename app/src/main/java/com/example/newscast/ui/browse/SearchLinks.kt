package com.example.newscast.ui.browse

/**
 * Handle navigation for all quick search links in the app
 */
sealed class SearchLinks {
    class World(val keyword: String = "World", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Us(val keyword: String = "America", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Politics(val keyword: String = "Politics", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Business(val keyword: String = "Business", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Tech(val keyword: String = "Tech", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Science(val keyword: String = "Science", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Sports(val keyword: String = "Sports", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Travel(val keyword: String = "Travel", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
    class Culture(val keyword: String = "Culture", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort) : SearchLinks()
}

/**
 * [DATE]: publishing date
 * [RELEVANCE]: relevance to the query
 * [SOURCE_IMPORTANCE]: manually curated score of source importance - high value, high importance
 * [SOURCE_ALEXA_GLOBAL_RANK]: global rank of the news source
 */
enum class ArticlesToSortBy(val sort: String) {
    DATE("date"),
    RELEVANCE("rel"),
    SOURCE_IMPORTANCE("sourceImportance"),
    SOURCE_ALEXA_GLOBAL_RANK("sourceAlexaGlobalRank")
}
