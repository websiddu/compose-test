package tv.compose.data.model.responses

import tv.compose.data.model.TvShow

data class TvShowListResponse(
  val results: List<TvShow>
)