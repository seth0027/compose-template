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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(petId: Int, overViewModel: OverViewModel) {

    val pet = overViewModel.getPet(petId).observeAsState(null)

    pet.value?.let {
        DetailView(it)
    }
}

@Composable
private fun DetailView(pet: Pet) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            CustomCoilImage(data = pet.photo2)
            Text(text = pet.name)
            Text(text = pet.breed)
            Text(text = pet.gender)
            Text(text = pet.age)
            Text(text = pet.size)
            Text(text = pet.description)
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun DetailViewPreview() {
    DetailView(pet = dummyList[0])
}
