package tv.compose.di

import android.os.Environment
import android.util.Log
import com.github.ajalt.timberkt.d
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tv.compose.BuildConfig
import tv.compose.data.api.TmdbService
import tv.compose.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
  
  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor { message -> d { message } }
    return logging.apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }
  }
  
  @Provides
  @Singleton
  fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
  )
      : OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    
    if (BuildConfig.DEBUG) {
      httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    httpClientBuilder.addNetworkInterceptor { chain ->
      var request = chain.request()
      val urlWithKey =
        request.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
          .build()
      request = request.newBuilder().url(urlWithKey).build()
      chain.proceed(request = request)
    }
    
    return httpClientBuilder
      .connectTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)
      .writeTimeout(1, TimeUnit.MINUTES)
      .cache(Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024))
      .build()
  }
  
  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }
  
  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
//        val contentType = "application/json".toMediaType()
    return Retrofit.Builder().apply {
      client(okHttpClient)
      baseUrl(Constants.URL.BASE_URL)
      addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    }.build()
  }
  
  @Provides
  @Singleton
  fun provideTmdbService(retrofit: Retrofit): TmdbService {
    return retrofit.create(TmdbService::class.java)
  }
}