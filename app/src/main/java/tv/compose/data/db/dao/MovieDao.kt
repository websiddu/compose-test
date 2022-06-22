package tv.compose.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tv.compose.data.model.Movie

@Dao
interface MovieDao {
  
  @Transaction
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(genres: List<Movie>)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(genre: Movie)
  
  @Transaction
  @Query("SELECT * FROM movie")
  fun getAll(): Flow<List<Movie>>
  
  @Query("SELECT * FROM movie WHERE movieId = :id")
  fun getMovie(id: Int): Flow<Movie>
}