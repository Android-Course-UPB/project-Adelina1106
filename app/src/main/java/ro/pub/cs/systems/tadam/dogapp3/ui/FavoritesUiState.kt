package ro.pub.cs.systems.tadam.dogapp3.ui

import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog

data class FavoritesUiState(
    val favorites: List<FavoriteDog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)