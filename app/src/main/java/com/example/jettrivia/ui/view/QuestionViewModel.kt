package com.example.jettrivia.ui.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.ResultWrapper
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository): ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading = _loading.value

    val data: MutableState<ResultWrapper<ArrayList<QuestionItem>, Exception>> =
        mutableStateOf(ResultWrapper(null, Exception("")))

    init {
        getQuestions()
    }

    private fun getQuestions() = viewModelScope.launch {
        _loading.value = true
        data.value = repository.getAll()
        _loading.value = false
    }
}