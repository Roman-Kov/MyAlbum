package com.rojer_ko.myalbum.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rojer_ko.myalbum.data.network.*
import com.rojer_ko.myalbum.data.repository.AlbumsDBRepositoryImpl
import com.rojer_ko.myalbum.data.repository.AlbumsRepositoryImpl
import com.rojer_ko.myalbum.data.retrofit.ApiService
import com.rojer_ko.myalbum.data.room.AlbumsDao
import com.rojer_ko.myalbum.data.room.DB
import com.rojer_ko.myalbum.domain.contracts.AlbumsDBRepository
import com.rojer_ko.myalbum.domain.contracts.AlbumsRepository
import com.rojer_ko.myalbum.presentation.albums.viewmodel.AlbumViewModel
import com.rojer_ko.myalbum.presentation.albums.viewmodel.AlbumsViewModel
import com.rojer_ko.myalbum.presentation.savedAlbums.viewmodel.SavedAlbumsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val interceptorsList = mutableListOf(
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
)

private fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .also { builder ->
            interceptorsList.forEach {
                builder.addInterceptor(it)
            }
        }
        .build()
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL_LOCATION)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

private fun getApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

private fun getDB(context: Context): DB {
    return Room.databaseBuilder(context, DB::class.java, "albumsDB").build()
}

private fun getAlbumDao(db: DB): AlbumsDao {
    return db.albumsDao()
}

val appModule = module {
    single { NetworkManager(androidContext()) }
    single { getOkHttpClient() }
    single { getRetrofit(get()) }
    single { getApi(get()) }
    single { getDB(androidContext()) }
    single { getAlbumDao(get()) }
    single<AlbumsRepository> { AlbumsRepositoryImpl(get(), get()) }
    single<AlbumsDBRepository> { AlbumsDBRepositoryImpl(get()) }

    //ViewModels
    viewModel { AlbumsViewModel(get()) }
    viewModel { AlbumViewModel(get(), get()) }
    viewModel { SavedAlbumsViewModel(get()) }
}