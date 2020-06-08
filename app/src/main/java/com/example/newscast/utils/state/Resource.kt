package com.example.newscast.utils.state

/**
 * Wrapper class for Network requests. Provides:
 * @property status: The [Status] of the network request
 * @property data: data received from network request
 * @property message: error message if applicable
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data,null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}