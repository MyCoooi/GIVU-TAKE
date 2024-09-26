package com.project.givuandtake.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room




@Entity(tableName = "gift_details")
data class GiftDetail(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val location: String
)


@Dao
interface GiftDetailDao {

    @Query("SELECT * FROM gift_details")
    fun getAllGiftDetails(): Flow<List<GiftDetail>>

    @Query("SELECT * FROM gift_details WHERE id IN (:ids)")
    fun getGiftDetailsByIds(ids: List<Int>): Flow<List<GiftDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiftDetails(giftDetails: List<GiftDetail>)

    @Query("DELETE FROM gift_details")
    suspend fun deleteAll()
}

@Database(entities = [GiftDetail::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDetailDao(): GiftDetailDao
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration() // 스키마 변경 시 데이터베이스를 재생성합니다.
                .build()
            INSTANCE = instance
            instance
        }
    }
}