package com.febiarifin.inponime

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.febiarifin.inponime.ui.navigation.NavigationItem
import com.febiarifin.inponime.ui.theme.InponimeTheme
import com.febiarifin.inponime.ui.navigation.Screen
import com.febiarifin.inponime.ui.screen.detail.DetailScreen
import com.febiarifin.inponime.ui.screen.home.HomeScreen

@Composable
fun InponimeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailAnime.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    }
                )
            }
//            composable(Screen.Favorite.route) {
//                val context = LocalContext.current
//                Favorite(
//                    onOrderButtonClicked = {message ->
//                        shareOrder(context, message)
//                    }
//                )
//            }
//            composable(Screen.About.route) {
//                ProfileScreen()
//            }
            composable(
                Screen.DetailAnime.route,
                arguments = listOf(navArgument("animeId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("animeId") ?: -1L
                DetailScreen(
                    animeId = id,
                    navigateBack = { navController.navigateUp() },
                    navigateToFavorite = {
//                        navController.popBackStack()
//                        navController.navigate(Screen.Favorite.route){
//                            popUpTo(navController.graph.findStartDestination().id){
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    InponimeTheme() {
        InponimeApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            )
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}