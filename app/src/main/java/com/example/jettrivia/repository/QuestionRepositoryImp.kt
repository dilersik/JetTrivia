package com.example.jettrivia.repository

import android.util.Log
import com.example.jettrivia.data.ResultWrapper
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.network.QuestionApi
import javax.inject.Inject


class QuestionRepositoryImp @Inject constructor(
    private val api: QuestionApi,
) : QuestionRepository {

    private val resultWrapperList = ResultWrapper<ArrayList<QuestionItem>, Exception>()

    override suspend fun getAll(): ResultWrapper<ArrayList<QuestionItem>, Exception> =
        with(resultWrapperList) {
            try {
                data = api.getAll()
            } catch (e: Exception) {
                exception = e
                Log.e(TAG, "getAll: ${e.localizedMessage}")
            }
            this
        }


    private companion object{
        private const val TAG = "QuestionRepositoryImp"
    }
}