package com.example.newscast.network

import android.content.SharedPreferences
import com.example.newscast.BuildConfig
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

/**
 * articlesSortBy = "rel" for relevant sources otherwise "date" to sort by new
 */
class NewsRequestBody(
    val action: String = "getArticles",
    val conceptUri: String = "https://en.wikipedia.org/wiki/News",
    val startSourceRankPercentile: Int =  0,
    val endSourceRankPercentile: Int = 30,
    val articlesPage: Int = 1,
    val articlesCount: Int = 30,
    val articlesSortBy: String = "sourceImportance",
    val articlesSortByAsc: Boolean = false,
    val articlesArticleBodyLen: Int = -1,
    val resultType: String = "articles",
    val includeArticleImage: Boolean = true,
    val dataType: Array<String> = arrayOf("news"),
    val apiKey: String = BuildConfig.API_KEY,
    var lang: String = "eng",
    val forceMaxDataTimeWindow: Int = 30
): KoinComponent {

    private val sharedPreferences: SharedPreferences by inject(named("preference_manager"))

    init {
        lang = sharedPreferences.getString("language", "eng") ?: "eng"
    }

    fun refresh() : NewsRequestBody {
        lang = sharedPreferences.getString("language", "eng") ?: "eng"
        return this
    }

    /**
     * Parameters

    apiKey

    string
    (required)
    Your API key

    resultType

    string
    (required)
    Define what kind of results of the search you would like to get. Default value: articles.

    Available values: articles, uriWgtList, langAggr, timeAggr, sourceAggr, sourceExAggr, authorAggr, keywordAggr, locAggr, conceptAggr, conceptGraph, categoryAggr, dateMentionAggr, sentimentAggr, recentActivityArticles
    articlesPage

    integer
    Determines the page of the results to return (starting from 1). Relevant when resultType = articles.

    Default value: 1
    articlesCount

    integer
    Define how many articles (up to 100) will be returned. Relevant when resultType = articles.

    Default value: 100
    articlesSortBy

    string
    Choose the criteria for sorting the news articles. rel (relevance to the query), date (publishing date), sourceImportance (manually curated score of source importance - high value, high importance), sourceImportanceRank (reverse of sourceImportance), sourceAlexaGlobalRank (global rank of the news source), sourceAlexaCountryRank (country rank of the news source), socialScore (total shares on social media), facebookShares (shares on Facebook only). Relevant when resultType = articles.

    Available values: date, rel, sourceImportance, sourceAlexaGlobalRank, sourceAlexaCountryRank, socialScore, facebookShares
    Default value: date
    articlesSortByAsc

    boolean
    Should the results be ordered in ascending order or descending order (default). Relevant when resultType = articles.

    articleBodyLen

    integer
    Set the size of the article body that'll be returned in the response. Use -1 for full article body.

    Default value: -1
    dataType

    string | string[]
    What data types should we search? news content (default, news), press releases (pr) or blogs (blog).

    Available values: news, pr, blog
    Default value: news
     */

}