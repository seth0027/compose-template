/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder

const val TAG = "ActivityDebug"
private val gson: Gson = GsonBuilder().disableHtmlEscaping().create()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                PetApp()
            }
        }
    }
}

@Composable
fun PetApp() {
    val title = remember { mutableStateOf("") }
    val showBack = remember { mutableStateOf(false) }
    val navController = rememberNavController()

    Scaffold(topBar = { PetTopBar(title = title.value, navController, showBack.value) }) {
        AppNavigation(navController) { navTitle, shouldBack ->
            title.value = navTitle
            showBack.value = shouldBack
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PetTopBar(title: String, navController: NavHostController, showBack: Boolean) {
    TopAppBar {
        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(6.dp))
            AnimatedVisibility(visible = showBack) {
                MyBack(navHostController = navController)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(text = title, style = MaterialTheme.typography.h5)
        }
    }
}

@Composable
private fun MyBack(navHostController: NavHostController) {
    IconButton(onClick = { navHostController.navigateUp() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow)
        )
    }
}

@Composable
private fun AppNavigation(
    navController: NavHostController,
    overViewModel: OverViewModel = viewModel(), navigationTitle: (String, Boolean) -> Unit
) {
    NavHost(navController, startDestination = "overview") {
        composable("overview") {
            navigationTitle("Pet List", false)
            OverViewScreen(overViewModel = overViewModel) { pet ->
                navigationTitle(pet.name, true)
                navController.navigate("details/${pet.id}")
            }
        }

        composable(
            "details/{petId}",
            arguments = listOf(
                navArgument("petId") {
                    type = NavType.IntType
                }
            )
        ) {
            it.arguments?.getInt("petId")?.let { petId ->
                DetailsScreen(petId = petId, overViewModel = overViewModel)
            }
        }
    }
}
