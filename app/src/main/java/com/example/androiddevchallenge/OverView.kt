package com.example.androiddevchallenge


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import java.io.IOException

class OverViewModel : ViewModel() {

    private val _pets = MutableLiveData<List<Pet>>()
    val pets: LiveData<List<Pet>> = _pets

    fun populateList(context: Context, fileName: String = "petModified.json"): Boolean {

        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }
        return getJsonDataFromAsset(context = context, fileName = fileName)?.let { json ->
            _pets.value = Gson().fromJson(json, Array<Pet>::class.java).toList()
            true
        } ?: false
    }

}

@Composable
fun OverViewScreen(
    context: Context,
    overViewModel: OverViewModel = viewModel(),
    onClick: (Pet) -> Unit
) {
    val isLoaded = remember { mutableStateOf(overViewModel.populateList(context = context)) }
    val petList by overViewModel.pets.observeAsState(listOf())
    OverViewContent(petList = petList, isLoaded.value, onClick)
}

@Composable
private fun OverViewContent(petList: List<Pet>, isLoaded: Boolean, onClick: (Pet) -> Unit) {
    if (isLoaded) {
        PetListView(pets = petList, onClick)
    } else {
        FailView()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PetListView(pets: List<Pet>, onClick: (Pet) -> Unit) {
    LazyColumn {
        items(pets) { pet ->

            ListItem(
                icon = {
                    CustomCoilImage(data = pet.photo1)
                },

                text = { Text(text = pet.name) },
                secondaryText = { Text(text = pet.breed) },
                modifier = Modifier.clickable { onClick(pet) })
        }
    }

}

@Preview
@Composable
private fun PetListViewPreview() {

    PetListView(
        pets = dummyList
    ) { pet ->


    }
}


@Composable
private fun FailView() {
    Card {
        Column {
            Text(
                text = stringResource(id = R.string.fail_message),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onError, background = MaterialTheme.colors.error
                )
            )
        }
    }
}


val dummyList: List<Pet>
    get() = listOf(
        Pet(
            id = 1,
            breed = "Dangerous",
            gender = "female",
            age = "baby",
            name = "Doggo",
            size = "small",
            description = "random description",
            photo1 = "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50655296/2/?bust=1614236462&width=100",
            photo2 = "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50655296/2/?bust=1614236462&width=300"
        )
    )

@Composable
fun CustomCoilImage(data: Any) {
    CoilImage(
        data = data,
        requestBuilder = { transformations(CircleCropTransformation()) }) { imageLoadState ->
        when (imageLoadState) {
            is ImageLoadState.Success -> Image(
                painter = imageLoadState.painter,
                contentDescription = stringResource(id = R.string.app_name)
            )
        }
    }
}