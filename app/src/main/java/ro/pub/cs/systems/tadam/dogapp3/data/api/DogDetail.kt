import com.google.gson.annotations.SerializedName

data class DogImage(
    val id: String,
    val url: String
)

data class DogBreed(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("bred_for") val bredFor: String? = null,
    @SerializedName("breed_group") val breedGroup: String? = null,
    @SerializedName("height") val height: BreedMeasurement? = null,
    @SerializedName("weight") val weight: BreedMeasurement? = null,
    @SerializedName("temperament") val temperament: String? = null,
    @SerializedName("life_span") val lifeSpan: String? = null,
)

data class BreedMeasurement(
    @SerializedName("imperial") val imperial: String?,
    @SerializedName("metric") val metric: String?
)