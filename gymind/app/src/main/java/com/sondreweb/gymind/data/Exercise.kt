package com.sondreweb.gymind.data

import android.content.Context
import com.akuleshov7.ktoml.Toml
import com.sondreweb.gymind.helpers.Utilities
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@Serializable
data class Exercise (
    val _id: Int?,
    val name: String,
    val description: String,
    val instructions: List<String>?,
    val tips: List<String>?,
    val icon: Int,
    val targets: Map<String, List<String>>?,
    val synergies: List<String>?,
    val muscleActivationMap: String?,
    val examples: List<String>?,
    val exercise_substitutions: List<Int>?){

    fun initialize_data(context: Context){
        val exerciseString: String = Utilities.getAssetsData(context, "data/exercises.toml")
        val exerciseTable = Toml.decodeFromString<Exercise>(exerciseString)
        print(exerciseTable)
    }
}