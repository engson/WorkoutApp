package com.sondreweb.gymind

sealed class NavigationItem (var route: String, var icon: Int, var title: String) {
    object Profile : NavigationItem("home", R.drawable.ic_profile_circle_24, "Home")
    object Workouts : NavigationItem("Workouts", R.drawable.ic_workout_24, "Workouts")
    object Exercises : NavigationItem("Exercises", R.drawable.ic_exercises_24, "Exercises")
}
