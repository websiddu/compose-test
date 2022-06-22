package tv.compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tv.compose.data.db.dao.GenreDao
import tv.compose.data.db.dao.MovieDao
import tv.compose.data.db.dao.TvShowDao
import tv.compose.data.model.Genre
import tv.compose.data.model.Movie
import tv.compose.data.model.TvShow

@Database(
  entities = [
    Genre::class,
    Movie::class,
    TvShow::class,
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(TmdbTypeConverters::class)
abstract class TmdbDatabase: RoomDatabase() {
  
  abstract fun genreDao(): GenreDao
  abstract fun movieDao(): MovieDao
  abstract fun tvShowDao(): TvShowDao
  
}