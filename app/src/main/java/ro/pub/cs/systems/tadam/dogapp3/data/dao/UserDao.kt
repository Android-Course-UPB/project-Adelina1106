package ro.pub.cs.systems.tadam.dogapp3.data.dao

import androidx.room.*
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}