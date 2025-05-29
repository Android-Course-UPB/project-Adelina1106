package ro.pub.cs.systems.tadam.dogapp3.data.repository

import kotlinx.coroutines.flow.Flow
import ro.pub.cs.systems.tadam.dogapp3.data.dao.FavoriteDogDao
import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog

class DogRepository(private val favoriteDogDao: FavoriteDogDao) {
    fun getAllFavorites(userId: Int): Flow<List<FavoriteDog>> = favoriteDogDao.getAllFavorites(userId)
    suspend fun getFavoriteById(id: Int, userId: Int): FavoriteDog? = favoriteDogDao.getFavoriteById(id, userId)
    suspend fun insertFavorite(favoriteDog: FavoriteDog) = favoriteDogDao.insertFavorite(favoriteDog)
    suspend fun deleteFavorite(favoriteDog: FavoriteDog) = favoriteDogDao.deleteFavorite(favoriteDog)
    suspend fun updateReview(id: Int, userId: Int, review: String) = favoriteDogDao.updateReview(id, userId, review)
    suspend fun isFavorite(id: Int, userId: Int): Boolean = favoriteDogDao.isFavorite(id, userId)
}