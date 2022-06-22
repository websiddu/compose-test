package tv.compose.data.api

import retrofit2.http.GET
import tv.compose.data.model.responses.GenreListResponse
import tv.compose.data.model.responses.MovieListResponse
import tv.compose.data.model.responses.TvShowListResponse


interface TmdbService {
  
  @GET("3/genre/movie/list")
  suspend fun getListOfMovieGenre(): GenreListResponse
  
  @GET("3/genre/tv/list")
  suspend fun getListOfTvShowGenre(): GenreListResponse
  
  @GET("3/trending/movie/week")
  suspend fun getTrendingMovies(): MovieListResponse
  
  @GET("3/trending/tv/week")
  suspend fun getTrendingShows(): TvShowListResponse
}