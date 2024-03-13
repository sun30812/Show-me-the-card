package com.example.showmethecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.showmethecard.ui.CardInfo
import com.example.showmethecard.ui.CardList
import com.example.showmethecard.ui.theme.ShowMeTheCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ShowMeTheCardTheme {
                MainScreen(navController = navController)
            }
        }
    }

}

@Composable
private fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = { MainTitleBar(navController) }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = "cards") {
                composable(Routes.Cards.route) {
                    CardList(navController = navController)
                }
                composable(
                    Routes.CardDetail.route,
                    arguments = listOf(navArgument("cardId") {
                        type = NavType.StringType
                    })
                ) {
                    CardInfo(cardId = it.arguments?.getString("cardId") ?: "")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTitleBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = { Text(
        text = "카드 목록"
    ) }, navigationIcon = {
        if (navController.previousBackStackEntry != null) {
            IconButton(onClick = {navController.navigateUp()}) {
                Icon(Icons.Outlined.ArrowBack, "back")
            }
        }


    })
}