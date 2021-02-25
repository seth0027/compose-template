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

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
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
                AppNavigation(context = this)
            }
        }
    }
}

@Composable
private fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "overview") {
        composable("overview") {
            OverViewScreen(context = context) { pet ->
                val petJson = gson.toJson(pet)
                Log.d("TAG", "petjson is : $petJson")
                navController.navigate("details/$petJson")
            }
        }

        composable(
            "details/{pet}",
            arguments = listOf(
                navArgument("pet") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("pet")?.let { petJson ->
                val pet =
                    gson.fromJson(petJson, Pet::class.java)
                DetailsScreen(pet = pet)
            }
        }
    }
}
