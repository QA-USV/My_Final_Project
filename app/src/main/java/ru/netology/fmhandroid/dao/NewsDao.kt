package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.fmhandroid.dto.NewsWithCategory
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.entity.NewsCategoryEntity
import ru.netology.fmhandroid.entity.NewsEntity

@Dao
interface NewsDao {
    @Transaction
    @Query("SELECT * FROM NewsEntity ORDER BY publishDate DESC")
    fun getAllNews(): Flow<List<NewsWithCreators>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsItem: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<NewsEntity>)

    @Query("DELETE FROM NewsEntity WHERE id = :id")
    suspend fun removeNewsItemById(id: Int)

    @Transaction
    @Query("SELECT * FROM NewsEntity WHERE newsCategoryId = :newsCategoryId ORDER BY publishDate DESC")
    fun filterNewsByCategory(newsCategoryId: Int): Flow<List<NewsWithCreators>>

    @Transaction
    @Query("SELECT * FROM NewsEntity WHERE publishDate BETWEEN :dateStart AND :dateEnd ORDER BY publishDate DESC")
    fun filterNewsByPublishDate(dateStart: Long, dateEnd: Long): Flow<List<NewsWithCreators>>

    @Transaction
    @Query("""
        SELECT * FROM NewsEntity WHERE
        newsCategoryId = :newsCategoryId AND publishDate BETWEEN :dateStart AND :dateEnd
        OR publishDate = :dateStart
        ORDER BY publishDate DESC
    """)
    fun filterNewsByCategoryAndPublishDate(
        newsCategoryId: Int,
        dateStart: Long,
        dateEnd: Long
    ): Flow<List<NewsWithCreators>>
}

@Dao
interface NewsCategoryDao {
    @Query("SELECT * FROM NewsCategoryEntity ORDER BY id")
    fun getAllNewsCategories(): Flow<List<NewsCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<NewsCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: NewsCategoryEntity)
}