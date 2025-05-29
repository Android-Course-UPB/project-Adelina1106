package ro.pub.cs.systems.tadam.dogapp3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import ro.pub.cs.systems.tadam.dogapp3.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    onLogout: () -> Unit,
    viewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var breedInput by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.loadRandomDog()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DogApp", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate("favorites_screen") }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            tint = Color.Red
                        )
                    }
                    Button(
                        onClick = onLogout,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Logout")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.error != null) {
                    Text("Error: ${uiState.error}")
                } else if (uiState.randomDogUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(uiState.randomDogUrl),
                        contentDescription = "Random Dog",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Text(
                        text = "Your daily dose of random dogs!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                TextField(
                    value = breedInput,
                    onValueChange = { breedInput = it },
                    label = { Text("Enter dog breed (e.g., beagle, bulldog)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val breed = breedInput.text.trim()
                        if (breed.isNotEmpty()) {
                            navController.navigate("dog_detail_screen/$breed")
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Search Breed")
                }
            }
        }
    )
}