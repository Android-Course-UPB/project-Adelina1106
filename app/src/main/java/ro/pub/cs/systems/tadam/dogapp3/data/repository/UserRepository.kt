package ro.pub.cs.systems.tadam.dogapp3.data.repository

import ro.pub.cs.systems.tadam.dogapp3.data.dao.UserDao
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByUsername(username: String): User? = userDao.getUserByUsername(username)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
}