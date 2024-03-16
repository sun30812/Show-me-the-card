package com.example.showmethecard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.showmethecard.ui.CardInfoPage
import com.example.showmethecard.ui.CardList

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val currentDestination by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            MainTitleBar(
                title = when (currentDestination?.destination?.route) {
                    "cards" -> stringResource(Routes.Cards.descriptionResourceId)
                    else -> stringResource(id = Routes.CardDetail.descriptionResourceId)
                },
                isCanGoBack = navController.previousBackStackEntry != null,
                navigateUp = navController::navigateUp
            )
        }
    ) {
        NavHost(
            navController = navController, startDestination = "cards",
            modifier = Modifier
                .padding(it)
        ) {
            composable(Routes.Cards.route) {
                CardList(onNavigateButtonClick = { navigateLink ->
                    navController.navigate(navigateLink)
                })
            }
            composable(
                Routes.CardDetail.route,
                arguments = listOf(navArgument("cardId") {
                    type = NavType.StringType
                })
            ) { it: NavBackStackEntry ->
                CardInfoPage(cardId = it.arguments?.getString("cardId") ?: "")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTitleBar(title: String?, isCanGoBack: Boolean, navigateUp: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = title ?: stringResource(id = Routes.Cards.descriptionResourceId)
        )
    }, navigationIcon = {
        if (isCanGoBack) {
            IconButton(onClick = navigateUp) {
                Icon(Icons.Outlined.ArrowBack, "back")
            }
        }


    })
}