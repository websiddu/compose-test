package tv.compose.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tv.compose.data.model.TvShow


@Dao
interface TvShowDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(genres: List<TvShow>)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(genre: TvShow)
  
  @Transaction
  @Query("SELECT * FROM tv_show")
  fun getAll(): Flow<List<TvShow>>
  
  @Query("SELECT * FROM tv_show WHERE tvShowId = :id")
  fun getShow(id: Int): Flow<TvShow>
}