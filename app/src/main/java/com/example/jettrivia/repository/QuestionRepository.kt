package com.example.jettrivia.repository

import com.example.jettrivia.data.ResultWrapper
import com.example.jettrivia.model.QuestionItem

interface QuestionRepository {
    suspend fun getAll(): ResultWrapper<ArrayList<QuestionItem>, Boolean, Exception>
}