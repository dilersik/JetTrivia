package com.example.jettrivia.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.ui.theme.DarkPurple
import com.example.jettrivia.ui.theme.LightBlue
import com.example.jettrivia.ui.theme.LightGray
import com.example.jettrivia.ui.theme.LightPurple
import com.example.jettrivia.ui.theme.OffWhite
import com.example.jettrivia.ui.theme.Pink80
import com.example.jettrivia.ui.theme.Purple40
import com.example.jettrivia.ui.theme.Purple80
import com.example.jettrivia.ui.theme.PurpleGrey80

@Composable
fun TriviaHome(viewModel: QuestionViewModel) = Questions(viewModel)

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember { mutableIntStateOf(0) }

    if (viewModel.data.value.loading == true)
        CircularProgressIndicator(modifier = Modifier.size(20.dp))
    else {
        val question = try {
            questions?.get(questionIndex.intValue)
        } catch (e: Exception) {
            null
        }
        question?.let {
            QuestionDisplay(viewModel = viewModel, question = it, questionIndex = questionIndex) {
                questionIndex.intValue += 1
            }
        }
    }
}

@Composable
fun QuestionDisplay(
    viewModel: QuestionViewModel,
    question: QuestionItem,
    questionIndex: MutableIntState,
    onNextClicked: (Int) -> Unit
) {
    val choicesState = remember(question) { question.choices.toMutableList() }
    val answerState = remember(question) { mutableStateOf<Int?>(null) }
    val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkPurple
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            val outOf = viewModel.data.value.data?.size ?: run { 0 }
            QuestionTracker(counter = questionIndex.intValue, outOf = outOf)
            DottedLine(pathEffect)
            Column(modifier = Modifier.fillMaxSize()) {
                ShowProgress(score = questionIndex.intValue)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxHeight(0.3f),
                    text = question.question,
                    color = OffWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
                choicesState.forEachIndexed { index, choiceText ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(54.dp)
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(listOf(Purple80, Purple80)),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(RoundedCornerShape(50))
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val color = if (index == answerState.value) {
                            if (correctAnswerState.value == true) Color.Green
                            else Color.Red
                        } else OffWhite
                        RadioButton(
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(unselectedColor = Purple80, selectedColor = color),
                            selected = (answerState.value == index),
                            onClick = { updateAnswer(index) }
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Medium, color = color, fontSize = 16.sp)) {
                                append(choiceText)
                            }
                        }
                        Text(modifier = Modifier.padding(6.dp), text = annotatedString)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally), shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                    onClick = { onNextClicked(questionIndex.intValue) }
                ) {
                    Text(modifier = Modifier.padding(4.dp), text = "Next", color = OffWhite, fontSize = 18.sp)
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
                append("Question ${counter + 1}")
                withStyle(SpanStyle(color = LightGray, fontWeight = FontWeight.Light, fontSize = 14.sp)) {
                    append(" / $outOf")
                }
            }
        })
}

@Composable
fun ShowProgress(score: Int = 0) {
    val gradient = Brush.linearGradient(listOf(Purple40, Pink80))
    val progressFactor = remember(score) { mutableFloatStateOf(score * 0.005f) }
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(44.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(colors = listOf(LightPurple, LightPurple)),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(RoundedCornerShape(50))
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(progressFactor.floatValue)
                .background(brush = gradient),
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            onClick = {}
        ) {
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(6.dp),
                text = (score * 10).toString(),
                color = OffWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}