package com.sondreweb.gymind

import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sondreweb.gymind.ui.theme.GyMindTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GyMindTheme() {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primarySurface, modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

// TODO: Fix state between bottom and top bar
// https://github.com/johncodeos-blog/BottomNavigationBarComposeExample/blob/main/app/src/main/java/com/example/bottomnavigationbarcomposeexample/MainActivity.kt
 // https://github.com/farida-techie/BottomNavigationBarS/blob/master/app/src/main/java/com/malkinfo/bottomnavigationbars/MainActivity.kt
@Composable
fun ContentNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Profile.route) {
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavigationItem.Workouts.route) {
            WorkoutsScreen()
        }
        composable(NavigationItem.Exercises.route) {
            ExercisesScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var title by remember{ mutableStateOf("Profile")}
    Scaffold(
        topBar = { TopBar(navController)},
        bottomBar = { BottomNavigationBar(navController)}
    ) {
        ContentNavigation(navController = navController)
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun TopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val title by remember { mutableStateOf(currentRoute) }
    TopAppBarComp("HelloWorld")
}

class TopAppBarProvider : PreviewParameterProvider<String> {
    override val values = listOf("Exercises", "Workouts").asSequence()
}


@Composable
fun TopAppBarComp(title: String){
    TopAppBar (
        title = {Text(text = title, fontSize = 18.sp)},
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarPreview(@PreviewParameter(TopAppBarProvider::class) title: String){
    TopAppBarComp(title)
}

@Composable
fun BottomNavigationBar(navController: NavController){
    val items = listOf(
        NavigationItem.Profile,
        NavigationItem.Workouts,
        NavigationItem.Exercises
    )
    BottomNavigation (Modifier.background(MaterialTheme.colors.background)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title)},
                label = { Text(text = item.title)},
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    //BottomNavigationBar()
}