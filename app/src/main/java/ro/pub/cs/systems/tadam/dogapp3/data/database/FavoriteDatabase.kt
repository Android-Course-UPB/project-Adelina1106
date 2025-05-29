package ro.pub.cs.systems.tadam.dogapp3.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import ro.pub.cs.systems.tadam.dogapp3.data.dao.FavoriteDogDao
import ro.pub.cs.systems.tadam.dogapp3.data.dao.UserDao
import ro.pub.cs.systems.tadam.dogapp3.data.entities.FavoriteDog
import ro.pub.cs.systems.tadam.dogapp3.data.entities.User

@Database(
    entities = [FavoriteDog::class, User::class],
    version = 2
)
abstract class DogDatabase : RoomDatabase() {
    abstract fun favoriteDogDao(): FavoriteDogDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "dog_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}