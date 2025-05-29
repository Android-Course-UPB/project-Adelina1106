package ro.pub.cs.systems.tadam.dogapp3.ui

data class MainUiState(
    val randomDogUrl: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)