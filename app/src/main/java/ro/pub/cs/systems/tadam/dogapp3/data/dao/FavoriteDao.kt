package ro.pub.cs.systems.tadam.dogapp3.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog

@Dao
interface FavoriteDogDao {
    @Query("SELECT * FROM favorite_dogs WHERE userId = :userId ORDER BY dateAdded DESC")
    fun getAllFavorites(userId: Int): Flow<List<FavoriteDog>>

    @Query("SELECT * FROM favorite_dogs WHERE id = :id AND userId = :userId")
    suspend fun getFavoriteById(id: Int, userId: Int): FavoriteDog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteDog: FavoriteDog)

    @Delete
    suspend fun deleteFavorite(favoriteDog: FavoriteDog)

    @Query("UPDATE favorite_dogs SET review = :review WHERE id = :id AND userId = :userId")
    suspend fun updateReview(id: Int, userId: Int, review: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_dogs WHERE id = :id AND userId = :userId)")
    suspend fun isFavorite(id: Int, userId: Int): Boolean
}