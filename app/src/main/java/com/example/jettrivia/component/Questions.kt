package com.example.jettrivia.component

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jettrivia.ui.view.QuestionViewModel

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if (viewModel.data.value.loading == true)
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    else
        Toast.makeText(LocalContext.current, "Loaded ${questions?.size}", Toast.LENGTH_SHORT).show()
}