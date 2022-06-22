package tv.compose.data.model.responses

import tv.compose.data.model.Movie

data class MovieListResponse(
  val results: List<Movie>
)