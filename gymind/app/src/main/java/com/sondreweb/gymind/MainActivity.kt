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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
                    // Navigation()
                    //Greeting("Android")
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
    TopAppBar (
        title = { title?.let { Text(text = it, fontSize = 18.sp) } },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarPreview(){
    //    TopBar()
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


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }

        composable("main_screen"){
            Greeting("Sondre123")
        }
    }
}

@Composable
fun SplashScreen(navController: NavController){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f)
                        .getInterpolation(it)
                }))
        delay(300L)

        navController.navigate("main_screen")
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }
}

@Composable
fun Greeting(name: String) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Hello $name!", color = MaterialTheme.colors.secondary, modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi  = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    GyMindTheme {
        Greeting("preview")
    }
}