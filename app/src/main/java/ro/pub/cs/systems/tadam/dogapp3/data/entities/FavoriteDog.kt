package ro.pub.cs.systems.tadam.dogapp3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dogs")
data class FavoriteDog(
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val imageUrl: String,
    val weight: String?,
    val height: String?,
    val bredFor: String?,
    val breedGroup: String?,
    val review: String? = null,
    val dateAdded: Long = System.currentTimeMillis()
)