package tv.compose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import tv.compose.data.api.TmdbService
import tv.compose.data.db.dao.GenreDao
import tv.compose.data.db.dao.MovieDao
import tv.compose.data.db.dao.TvShowDao
import tv.compose.data.repository.TmdbRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
  
  @Provides
  fun provideTmdbRepository(
    genreDao: GenreDao,
    movieDao: MovieDao,
    tvShowDao: TvShowDao,
    service: TmdbService,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
  ): TmdbRepository {
    return TmdbRepository(genreDao, movieDao, tvShowDao, service, ioDispatcher)
  }
}