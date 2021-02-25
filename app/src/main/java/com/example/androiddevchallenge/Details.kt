package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(pet: Pet) {

    DetailView(pet)


}

@Composable
private fun DetailView(pet: Pet) {
    Card(elevation = 4.dp) {
        Column {
            CustomCoilImage(data = pet.photo2)
            Text(text = pet.name)
            Text(text = pet.breed)
            Text(text = pet.gender)
            Text(text = pet.age)
            Text(text = pet.size)
        }
    }
}

@Preview
@Composable
private fun DetailViewPreview() {
    DetailView(pet = dummyList[0])
}