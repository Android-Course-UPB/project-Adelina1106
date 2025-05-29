import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DogApiService {

    @GET("v1/images/search")
    suspend fun getRandomDog(): Response<List<DogImage>>

    @GET("v1/images/search")
    suspend fun searchImageByBreed(
        @Query("breed_id") breedId: Int
    ): List<DogImage>

    @GET("v1/breeds")
    suspend fun getAllBreeds(
        @Header("x-api-key") apiKey: String
    ): Response<List<DogBreed>>
}
