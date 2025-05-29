package ro.pub.cs.systems.tadam.dogapp3.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.pub.cs.systems.tadam.dogapp3.data.repository.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username, error = null)
    }

    fun login(onLogin: (User) -> Unit) {
        val username = _uiState.value.username
        if (username.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Username cannot be empty")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            var user = userRepository.getUserByUsername(username)
            if (user == null) {
                userRepository.insertUser(User(username = username))
                user = userRepository.getUserByUsername(username)
            }
            if (user != null) {
                onLogin(user)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } else {
                _uiState.value = _uiState.value.copy(error = "Login failed", isLoading = false)
            }
        }
    }
}

class LoginViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}