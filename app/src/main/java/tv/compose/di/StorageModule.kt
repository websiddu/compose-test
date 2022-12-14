package tv.compose.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tv.compose.data.db.TmdbDatabase
import tv.compose.data.db.TmdbTypeConverters
import tv.compose.data.db.dao.GenreDao
import tv.compose.data.db.dao.MovieDao
import tv.compose.data.db.dao.TvShowDao
import tv.compose.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
  
  @Provides
  @Singleton
  fun provideTmdbTypeConverter(moshi: Moshi): TmdbTypeConverters {
    return TmdbTypeConverters(moshi)
  }
  
  @Provides
  @Singleton
  fun provideTmdbDatabase(application: Application, tmdbTypeConverters: TmdbTypeConverters): TmdbDatabase {
    return Room.databaseBuilder(
      application,
      TmdbDatabase::class.java,
      Constants.Database.DB_NAME
    )
      .addTypeConverter(tmdbTypeConverters)
      .build()
  }
  
  @Provides
  @Singleton
  fun provideGenreDao(db: TmdbDatabase): GenreDao {
    return db.genreDao()
  }
  
  @Provides
  @Singleton
  fun provideMovieDao(db: TmdbDatabase): MovieDao {
    return db.movieDao()
  }
  
  @Provides
  @Singleton
  fun provideTvShowDao(db: TmdbDatabase): TvShowDao {
    return db.tvShowDao()
  }
}