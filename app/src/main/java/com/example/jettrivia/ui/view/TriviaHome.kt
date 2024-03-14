package com.example.jettrivia.ui.view

import androidx.compose.runtime.Composable
import com.example.jettrivia.component.Questions

@Composable
fun TriviaHome(viewModel: QuestionViewModel) {
    Questions(viewModel)
}