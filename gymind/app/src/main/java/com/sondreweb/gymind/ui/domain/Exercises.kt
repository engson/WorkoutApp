package com.sondreweb.gymind.ui.domain

import android.content.res.Configuration
import androidx.collection.ArrayMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sondreweb.gymind.R
import com.sondreweb.gymind.data.Exercise

@Composable
fun ExerciseItem(exercise: Exercise){
    Row(verticalAlignment = Alignment.CenterVertically){
        Image(
            painter = painterResource(id = exercise.icon), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape))
        Column() {
            Text(exercise.name)
            Text(exercise.description, fontSize = 10.sp)
        }
    }
}

class ExerciseItemProvider : PreviewParameterProvider<Exercise> {
    override val values = sequenceOf(
        Exercise(
            0,"Deadlift", "Deadlift with barbell",
            listOf("Stand with your mid-foot under the barbell.","Bend over and grab the bar with a shoulder-width grip."),
            listOf("Focus on pressing DOWN into the floor with your feet through your heels"),
            android.R.drawable.presence_online,
            mapOf(Pair("legs",listOf("glutes","hamstring")),Pair("back", listOf("Erector",""))),
            listOf("Quadriceps", "Hamstrings"),
            "muscleActivationMap",
            listOf("Example_1","Example_2"),
            listOf(1,4)
        ),
        Exercise(
            0,"Squat", "Squat with barbell",
            listOf("Stand with your mid-foot under the barbell.","Bend over and grab the bar with a shoulder-width grip."),
            listOf("Focus on pressing DOWN into the floor with your feet through your heels"),
            android.R.drawable.btn_star_big_on,
            mapOf(Pair("legs",listOf("glutes","hamstring")),Pair("back", listOf("Erector",""))),
            listOf("Quadriceps", "Hamstrings"),
            "muscleActivationMap",
            listOf("Example_1","Example_2"),
            listOf(1,4)
        )
    )
}

class ExercisesListProvider : PreviewParameterProvider<List<Exercise>> {
    override val values: Sequence<List<Exercise>> = sequenceOf(
        listOf(
            Exercise(
                0,"Deadlift", "Deadlift with barbell",
                listOf("Stand with your mid-foot under the barbell.","Bend over and grab the bar with a shoulder-width grip."),
                listOf("Focus on pressing DOWN into the floor with your feet through your heels"),
                android.R.drawable.presence_online,
                mapOf(Pair("legs",listOf("glutes","hamstring")),Pair("back", listOf("Erector",""))),
                listOf("Quadriceps", "Hamstrings"),
                "muscleActivationMap",
                listOf("Example_1","Example_2"),
                listOf(1,4)
            ),
            Exercise(
                0,"Squat", "Squat with barbell",
                listOf("Stand with your mid-foot under the barbell.","Bend over and grab the bar with a shoulder-width grip."),
                listOf("Focus on pressing DOWN into the floor with your feet through your heels"),
                android.R.drawable.btn_star_big_on,
                mapOf(Pair("legs",listOf("glutes","hamstring")),Pair("back", listOf("Erector",""))),
                listOf("Quadriceps", "Hamstrings"),
                "muscleActivationMap",
                listOf("Example_1","Example_2"),
                listOf(1,4)
            )
        )
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ExerciseItemPreview(@PreviewParameter(ExerciseItemProvider::class) exercise: Exercise){
    ExerciseItem(exercise)
}

@Composable
fun Exercises(exercises: List<Exercise>){
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(2.dp)
    ){
        items(exercises) {
            exercise -> ExerciseItem(exercise)
        }
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ExercisesListPreview(@PreviewParameter(ExercisesListProvider::class) exercises: List<Exercise>){
    Exercises(exercises)
}