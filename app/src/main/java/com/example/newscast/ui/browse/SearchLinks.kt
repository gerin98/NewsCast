package com.example.newscast.ui.browse

/**
 * Handle navigation for all quick search links in the app
 */
sealed class SearchLinks {
    class World(val conceptUri: String = "https://en.wikipedia.org/wiki/World", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "World") : SearchLinks()
    class Us(val conceptUri: String = "https://en.wikipedia.org/wiki/United_States", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "US") : SearchLinks()
    class Politics(val conceptUri: String = "https://en.wikipedia.org/wiki/Politics", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Politics") : SearchLinks()
    class Business(val conceptUri: String = "https://en.wikipedia.org/wiki/Business", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Business") : SearchLinks()
    class Tech(val conceptUri: String = "https://en.wikipedia.org/wiki/Technology", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Tech") : SearchLinks()
    class Science(val conceptUri: String = "https://en.wikipedia.org/wiki/Science", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Science") : SearchLinks()
    class Sports(val conceptUri: String = "https://en.wikipedia.org/wiki/Sport", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Sports") : SearchLinks()
    class Travel(val conceptUri: String = "https://en.wikipedia.org/wiki/Travel", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Travel") : SearchLinks()
    class Culture(val conceptUri: String = "https://en.wikipedia.org/wiki/Culture", val articlesSortBy: String = ArticlesToSortBy.RELEVANCE.sort, val title: String = "Culture") : SearchLinks()
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
