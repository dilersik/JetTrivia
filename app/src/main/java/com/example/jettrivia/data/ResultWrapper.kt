package com.example.jettrivia.data

data class ResultWrapper<T, Exception>(
    var data: T? = null,
    var exception: Exception? = null
)
