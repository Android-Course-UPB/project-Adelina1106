package ro.pub.cs.systems.tadam.dogapp3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.pub.cs.systems.tadam.dogapp3.data.database.DogDatabase
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.collectAsState
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User
import ro.pub.cs.systems.tadam.dogapp3.data.repository.UserRepository
import ro.pub.cs.systems.tadam.dogapp3.ui.LoginViewModelFactory

@Composable
fun LoginScreen(
    onLogin: (User) -> Unit
) {
    val context = LocalContext.current
    val db = DogDatabase.getDatabase(context)
    val userRepository = UserRepository(db.userDao())
    val factory = LoginViewModelFactory(userRepository)
    val viewModel: ro.pub.cs.systems.tadam.dogapp3.ui.LoginViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        TextField(
            value = uiState.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Username") }
        )
        uiState.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login(onLogin) },
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Login")
            }
        }
    }
}