package com.example.jettrivia.repository

import android.util.Log
import com.example.jettrivia.data.ResultWrapper
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.network.QuestionApi
import javax.inject.Inject


class QuestionRepositoryImp @Inject constructor(
    private val api: QuestionApi,
) : QuestionRepository {

    override suspend fun getAll(): ResultWrapper<ArrayList<QuestionItem>, Boolean, Exception> {
        val resultWrapperList = ResultWrapper<ArrayList<QuestionItem>, Boolean, Exception>()
        try {
            resultWrapperList.data = api.getAll()
        } catch (e: Exception) {
            resultWrapperList.exception = e
            Log.e(TAG, "getAll: ${e.localizedMessage}")
        }
        return resultWrapperList
    }

    private companion object {
        private const val TAG = "QuestionRepositoryImp"
    }
}