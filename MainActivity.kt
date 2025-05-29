@Composable
fun AppNavigation(currentUser: User, onLogout: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(navController, currentUser.id, onLogout)
        }
        // ...other composables...
    }
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        var currentUser by rememberSaveable { mutableStateOf<User?>(null) }
        DogApp3Theme {
            if (currentUser == null) {
                LoginScreen { user -> currentUser = user }
            } else {
                AppNavigation(currentUser!!) {
                    currentUser = null // This logs out the user
                }
            }
        }
    }
}