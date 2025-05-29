package ro.pub.cs.systems.tadam.dogapp3.ui

import DogApiService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dogApi: DogApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))
    val uiState: StateFlow<MainUiState> = _uiState

    fun loadRandomDog() {
        viewModelScope.launch {
            _uiState.value = MainUiState(isLoading = true)
            try {
                val response = dogApi.getRandomDog()
                if (response.isSuccessful) {
                    val url = response.body()?.firstOrNull()?.url ?: ""
                    _uiState.value = MainUiState(randomDogUrl = url, isLoading = false)
                } else {
                    _uiState.value = MainUiState(error = "Failed to load dog", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = MainUiState(error = e.message, isLoading = false)
            }
        }
    }
}

class MainViewModelFactory(
    private val dogApi: DogApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dogApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}