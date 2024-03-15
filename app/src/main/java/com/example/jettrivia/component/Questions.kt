package com.example.jettrivia.component

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.ui.theme.DarkPurple
import com.example.jettrivia.ui.theme.LightGray
import com.example.jettrivia.ui.theme.OffWhite
import com.example.jettrivia.ui.theme.PurpleGrey80
import com.example.jettrivia.ui.view.QuestionViewModel

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if (viewModel.data.value.loading == true)
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    else
        Toast.makeText(LocalContext.current, "Loaded ${questions?.size}", Toast.LENGTH_SHORT).show()
}

@Composable
fun QuestionDisplay(
    viewModel: QuestionViewModel,
    question: QuestionItem,
    questionIndex: MutableIntState,
    onNextClicked: (Int) -> Unit
) {
    val choicesState = remember(question) { question.choices.toMutableList() }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = DarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            QuestionTracker(counter = 1, outOf = 100)
            DottedLine(pathEffect)
            Column {
                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxHeight(),
                    text = "Asadad",
                    color = OffWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
                choicesState.forEachIndexed { index, s ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(listOf(DarkPurple, DarkPurple)),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(RoundedCornerShape(50))
                            .background(Color.Transparent)
                    ) {
                    }
                }
            }
        }
    }
}

@Composable
fun DottedLine(pathEffect: PathEffect) {
    Spacer(modifier = Modifier.height(10.dp))
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {
        drawLine(
            color = PurpleGrey80,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun QuestionTracker(counter: Int, outOf: Int) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = LightGray, fontWeight = FontWeight.Bold, fontSize = 28.sp)) {
                append("Question $counter ")
                withStyle(SpanStyle(color = LightGray, fontWeight = FontWeight.Light, fontSize = 14.sp)) {
                    append(" / $outOf")
                }
            }
        })
}