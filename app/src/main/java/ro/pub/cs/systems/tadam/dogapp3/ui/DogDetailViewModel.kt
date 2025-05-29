package ro.pub.cs.systems.tadam.dogapp3.ui

import DogApiService
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.pub.cs.systems.tadam.dogapp3.data.repository.DogRepository
import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog

class DogDetailViewModel(
    private val repository: DogRepository,
    private val dogApi: DogApiService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DogDetailUiState(isLoading = true))
    val uiState: StateFlow<DogDetailUiState> = _uiState

    fun loadDogDetails(breed: String, userId: Int) {
        viewModelScope.launch {
            _uiState.value = DogDetailUiState(isLoading = true)
            try {
                val breedList = dogApi.getAllBreeds("live_2AOOfxdOR47fS9DbzjaEm29DU5iACKrSIKgApDSfAx2xKDISfdy5GiY4XikLRXbm").body() ?: emptyList()
                val breedInfo = breedList.find { it.name.contains(breed, ignoreCase = true) }
                if (breedInfo != null) {
                    val images = dogApi.searchImageByBreed(breedInfo.id)
                    val dogImageUrl = images.firstOrNull()?.url ?: ""
                    val isFavorite = repository.isFavorite(breedInfo.id, userId)
                    val favorite = repository.getFavoriteById(breedInfo.id, userId)
                    val temperamentWords = breedInfo.temperament
                        ?.split(",")
                        ?.map { it.trim().lowercase() }
                        ?.toSet()
                        ?: emptySet()

                    val recommended = breedList.filter {
                        it.id != breedInfo.id &&
                        temperamentWords.isNotEmpty() &&
                        it.temperament?.split(",")
                            ?.map { t -> t.trim().lowercase() }
                            ?.let { otherTemps ->
                                otherTemps.count { t -> temperamentWords.contains(t) } >= 3
                            } == true
                    }.take(3)

                    _uiState.value = DogDetailUiState(
                        breedId = breedInfo.id,
                        breedName = breedInfo.name,
                        dogImageUrl = dogImageUrl,
                        isFavorite = isFavorite,
                        review = favorite?.review.orEmpty(),
                        isLoading = false,
                        weight = breedInfo.weight?.metric,
                        height = breedInfo.height?.metric,
                        bredFor = breedInfo.bredFor,
                        breedGroup = breedInfo.breedGroup,
                        temperament = breedInfo.temperament,
                        lifeSpan = breedInfo.lifeSpan,
                        recommendedBreeds = recommended
                    )
                } else {
                    _uiState.value = DogDetailUiState(error = "Breed not found", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = DogDetailUiState(error = e.message, isLoading = false)
            }
        }
    }

    fun toggleFavorite(userId: Int) {
        val state = _uiState.value
        viewModelScope.launch {
            val breedId = state.breedId ?: return@launch
            if (state.isFavorite) {
                repository.getFavoriteById(breedId, userId)?.let { favoriteDog ->
                    repository.deleteFavorite(favoriteDog)
                }
            } else {
                repository.insertFavorite(
                    FavoriteDog(
                        id = breedId,
                        userId = userId,
                        name = state.breedName,
                        imageUrl = state.dogImageUrl,
                        weight = state.weight,
                        height = state.height,
                        bredFor = state.bredFor,
                        breedGroup = state.breedGroup,
                        review = state.review
                    )
                )
            }
            loadDogDetails(state.breedName, userId)
        }
    }

    fun saveReview(userId: Int, review: String) {
        val state = _uiState.value
        viewModelScope.launch {
            val breedId = state.breedId ?: return@launch
            repository.updateReview(breedId, userId, review)
            loadDogDetails(state.breedName, userId)
        }
    }

    fun downloadDogImage(context: Context, imageUrl: String) {
        if (imageUrl.isEmpty()) {
            Toast.makeText(context, "No image to download", Toast.LENGTH_SHORT).show()
            return
        }
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle("Dog Image")
            .setDescription("Downloading dog image")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "dog_${System.currentTimeMillis()}.jpg")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
    }
}

class DogDetailViewModelFactory(
    private val repository: DogRepository,
    private val dogApi: DogApiService
)   : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogDetailViewModel(repository, dogApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}