package tv.compose.ui.vm

import android.util.Log
import androidx.lifecycle.*
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.fresh
import com.github.ajalt.timberkt.e
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tv.compose.data.model.MediaContentType
import tv.compose.data.model.Movie
import tv.compose.data.model.TvShow
import tv.compose.data.repository.TmdbRepository
import tv.compose.di.IoDispatcher
import tv.compose.utils.Constants
import javax.inject.Inject

@HiltViewModel
class TmdbViewModel @Inject constructor(
  private val repository: TmdbRepository,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
  
  fun getMovieById(movieId: Int) =
    liveData {
      emitSource(repository.getMovieById(movieId).map { Success(it) }
        .asLiveData(ioDispatcher))
    }
  
  fun getTvShowById(showId: Int) =
    liveData {
      emitSource(repository.getTvShowById(showId).map { Success(it) }
        .asLiveData(ioDispatcher))
    }
  
  private val mode = MutableLiveData<MediaContentType>()
  
  fun setUiMode(type: MediaContentType) {
    mode.value = type
  }
  
  fun getUiMode() = liveData<MediaContentType> { emitSource(mode) }
  
  fun getMovies() = liveData<UiState<List<Movie>>>(ioDispatcher) {
    repository.movieStore().stream(StoreRequest.cached(Constants.Database.MOVIE_TABLE, false))
      .collect { storeResponse ->
        e { "Store state $storeResponse" }
        when (storeResponse) {
          is StoreResponse.Loading -> emit(Loading())
          is StoreResponse.Data -> {
            val data = storeResponse.value
            if (data.isNotEmpty())
              emit(Success(data))
            else
              emit(Loading())
          }
          is StoreResponse.Error.Exception -> emit(
            Error(
              storeResponse.error.message ?: "Unable to fetch data!"
            )
          )
          is StoreResponse.Error.Message -> emit(Error(storeResponse.message))
        }
      }
  }
  
  fun getTvShows() = liveData<UiState<List<TvShow>>>(ioDispatcher) {
    repository.tvShowStore().stream(StoreRequest.cached(Constants.Database.TV_SHOW_TABLE, false))
      .collect { storeResponse ->
        
        Log.e("my-log", storeResponse.toString())
        
        when (storeResponse) {
          is StoreResponse.Loading -> emit(Loading())
          is StoreResponse.Data -> {
            val data = storeResponse.value
            if (data.isNotEmpty())
              emit(Success(data))
            else
              emit(Loading())
          }
          is StoreResponse.Error.Exception -> emit(
            Error(
              storeResponse.error.message ?: "Unable to fetch data!"
            )
          )
          is StoreResponse.Error.Message -> emit(Error(storeResponse.message))
          else -> {}
        }
      }
  }
  
  fun refreshMovies() {
    viewModelScope.launch {
      repository.movieStore().clearAll()
      repository.movieStore().fresh(Constants.Database.MOVIE_TABLE)
    }
  }
  
  fun refreshTvShows() {
    viewModelScope.launch {
      repository.tvShowStore().clearAll()
      repository.tvShowStore().fresh(Constants.Database.TV_SHOW_TABLE)
    }
  }
  
  fun refreshGenre() {
    viewModelScope.launch {
      repository.genreStore().fresh("")
    }
  }
  
}