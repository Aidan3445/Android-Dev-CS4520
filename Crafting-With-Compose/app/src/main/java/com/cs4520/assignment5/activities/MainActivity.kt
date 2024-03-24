package com.cs4520.assignment5.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment5.R
import com.cs4520.assignment5.composables.LoginScreen
import com.cs4520.assignment5.composables.ProductListScreen
import com.cs4520.assignment5.database.ProductDatabase
import com.cs4520.assignment5.models.ProductRepository
import com.cs4520.assignment5.navigation.Animations
import com.cs4520.assignment5.navigation.NavGraph
import com.cs4520.assignment5.viewmodels.ProductListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val productListViewModel =
                ProductListViewModel(
                    ProductRepository(
                        ProductDatabase.init(this).productDao(),
                    ),
                    this.application,
                )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.purple_700))
                            .padding(0.dp, 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                NavHost(
                    navController = navController,
                    startDestination = NavGraph.loginScreen,
                    enterTransition = { Animations.enterRight },
                    exitTransition = { Animations.exitLeft },
                    popEnterTransition = { Animations.enterLeft },
                    popExitTransition = { Animations.exitRight },
                ) {
                    composable(NavGraph.loginScreen) {
                        LoginScreen(navController = navController)
                    }
                    composable(NavGraph.productListScreen) {
                        ProductListScreen(productListViewModel)
                    }
                }
            }
        }
    }
}
