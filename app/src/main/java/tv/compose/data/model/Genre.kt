package tv.compose.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import tv.compose.utils.Constants

@Entity(tableName = Constants.Database.GENRE_TABLE)
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class Genre(
  @PrimaryKey
  @Json(name = "id") val genreId: Int = -1,
  val name: String = ""
) : Parcelable