package com.example.jettrivia.data

data class ResultWrapper<T, Boolean, Exception> (
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: Exception? = null
)
