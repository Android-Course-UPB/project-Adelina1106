package ro.pub.cs.systems.tadam.dogapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.pub.cs.systems.tadam.dogapp3.data.database.DogDatabase
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User
import ro.pub.cs.systems.tadam.dogapp3.data.repository.DogRepository
import ro.pub.cs.systems.tadam.dogapp3.ui.DogDetailViewModelFactory
import ro.pub.cs.systems.tadam.dogapp3.ui.FavoritesViewModelFactory
import ro.pub.cs.systems.tadam.dogapp3.ui.screens.FavoritesScreen
import ro.pub.cs.systems.tadam.dogapp3.ui.screens.LoginScreen
import ro.pub.cs.systems.tadam.dogapp3.ui.screens.DogDetailScreen
import ro.pub.cs.systems.tadam.dogapp3.ui.theme.DogApp3Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentUser by rememberSaveable { mutableStateOf<User?>(null) }
            DogApp3Theme {
                if (currentUser == null) {
                    LoginScreen { user -> currentUser = user }
                } else {
                    AppNavigation(
                        currentUser = currentUser!!,
                        onLogout = { currentUser = null }
                    )
                }
            }
        }
    }

    @Composable
    fun AppNavigation(currentUser: User, onLogout: () -> Unit) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main_screen") {
            composable("main_screen") {
                val dogApi = RetrofitClient.instance
                val factory = ro.pub.cs.systems.tadam.dogapp3.ui.MainViewModelFactory(dogApi)
                val viewModel: ro.pub.cs.systems.tadam.dogapp3.ui.MainViewModel = viewModel(factory = factory)

                ro.pub.cs.systems.tadam.dogapp3.ui.screens.MainScreen(
                    navController = navController,
                    onLogout = onLogout,
                    viewModel = viewModel
                )
            }
            composable("dog_detail_screen/{breed}") { backStackEntry ->
                val breed = backStackEntry.arguments?.getString("breed") ?: ""
                val context = LocalContext.current
                val database = DogDatabase.getDatabase(context)
                val repository = DogRepository(database.favoriteDogDao())
                val dogApi = RetrofitClient.instance

                val factory = DogDetailViewModelFactory(repository, dogApi)
                val viewModel: ro.pub.cs.systems.tadam.dogapp3.ui.DogDetailViewModel = viewModel(factory = factory)

                DogDetailScreen(
                    breed = breed,
                    navController = navController,
                    userId = currentUser.id,
                    viewModel = viewModel
                )
            }
            composable("favorites_screen") {
                val context = LocalContext.current
                val database = DogDatabase.getDatabase(context)
                val repository = DogRepository(database.favoriteDogDao())

                val factory = FavoritesViewModelFactory(repository, userId = currentUser.id)
                val viewModel: ro.pub.cs.systems.tadam.dogapp3.ui.FavoritesViewModel = viewModel(factory = factory)
                FavoritesScreen(navController, viewModel)
            }
        }
    }
}