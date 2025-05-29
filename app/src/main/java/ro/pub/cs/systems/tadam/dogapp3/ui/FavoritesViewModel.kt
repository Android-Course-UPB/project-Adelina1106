package ro.pub.cs.systems.tadam.dogapp3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog
import ro.pub.cs.systems.tadam.dogapp3.data.repository.DogRepository

class FavoritesViewModel(
    private val repository: DogRepository,
    private val userId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllFavorites(userId)
                .onEach { favorites ->
                    _uiState.value = FavoritesUiState(favorites = favorites, isLoading = false)
                }
                .catch { e ->
                    _uiState.value = FavoritesUiState(isLoading = false, error = e.message)
                }
                .collect()
        }
    }

    fun deleteFavorite(favoriteDog: FavoriteDog) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteDog)
        }
    }
}

class FavoritesViewModelFactory(
    private val repository: DogRepository,
    private val userId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}