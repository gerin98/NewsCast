package com.example.newscast.ui.browse

/**
 * Handle navigation for all quick search links in the app
 */
sealed class SearchLinks {
    class World(val conceptUri: String = ConceptUri.WORLD.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "World") : SearchLinks()
    class Us(val conceptUri: String = ConceptUri.US.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "US") : SearchLinks()
    class Politics(val conceptUri: String = ConceptUri.POLITICS.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Politics") : SearchLinks()
    class Business(val conceptUri: String = ConceptUri.BUSINESS.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Business") : SearchLinks()
    class Tech(val conceptUri: String = ConceptUri.TECH.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Tech") : SearchLinks()
    class Science(val conceptUri: String = ConceptUri.SCIENCE.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Science") : SearchLinks()
    class Sports(val conceptUri: String = ConceptUri.SPORTS.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Sports") : SearchLinks()
    class Travel(val conceptUri: String = ConceptUri.TRAVEL.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Travel") : SearchLinks()
    class Culture(val conceptUri: String = ConceptUri.CULTURE.sort, val articlesSortBy: String = ArticlesToSortBy.SOURCE_IMPORTANCE.sort, val title: String = "Culture") : SearchLinks()
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

/**
 * The API uses wikipedia urls as their concept uris. These will provide results for what the search
 * entity represents instead of directly searching for the keyword
 */
enum class ConceptUri(val sort: String) {
    NEWS("https://en.wikipedia.org/wiki/News"),
    WORLD("https://en.wikipedia.org/wiki/World"),
    US("https://en.wikipedia.org/wiki/United_States"),
    POLITICS("https://en.wikipedia.org/wiki/Politics"),
    BUSINESS("https://en.wikipedia.org/wiki/Business"),
    TECH("https://en.wikipedia.org/wiki/Technology"),
    SCIENCE("https://en.wikipedia.org/wiki/Science"),
    SPORTS("https://en.wikipedia.org/wiki/Sport"),
    TRAVEL("https://en.wikipedia.org/wiki/Travel"),
    CULTURE("https://en.wikipedia.org/wiki/Culture")
}
