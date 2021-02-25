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

data class Pet(
    val id: Int,
    val breed: String,
    val age: String,
    val gender: String,
    val size: String,
    val name: String,
    val description: String,
    val photo1: String,
    val photo2: String
)

// {
//    "id": 50655296,
//    "breed": "Terrier",
//    "age": "Baby",
//    "gender": "Female",
//    "size": "Small",
//    "name": "Lola",
//    "photo1": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50655296/2/?bust=1614236462&width=100",
//    "photo2": "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50655296/2/?bust=1614236462&width=300",
//    "description": "Sweet and playful puppy Lola should stay 15 pounds or less. Ready for her forever home"
// }
