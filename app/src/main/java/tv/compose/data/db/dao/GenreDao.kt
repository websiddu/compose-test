package tv.compose.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tv.compose.data.model.Genre

@Dao
interface GenreDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(genres: List<Genre>)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(genre: Genre)
  
  @Query("SELECT * FROM genre")
  fun getAll(): Flow<List<Genre>>
  
  @Query("SELECT * FROM genre WHERE genreId IN (:ids)")
  fun getGenres(ids: List<Int>): List<Genre>
}