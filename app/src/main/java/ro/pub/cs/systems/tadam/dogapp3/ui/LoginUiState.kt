package ro.pub.cs.systems.tadam.dogapp3.ui

data class LoginUiState(
    val username: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)