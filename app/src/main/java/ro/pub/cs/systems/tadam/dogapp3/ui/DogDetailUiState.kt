package ro.pub.cs.systems.tadam.dogapp3.ui

import DogBreed

data class DogDetailUiState(
    val breedId: Int? = null,
    val breedName: String = "",
    val dogImageUrl: String = "",
    val isFavorite: Boolean = false,
    val review: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val bredFor: String? = null,
    val breedGroup: String? = null,
    val recommendedBreeds: List<DogBreed> = emptyList(),
    val temperament: String? = null,
    val lifeSpan: String? = null
)