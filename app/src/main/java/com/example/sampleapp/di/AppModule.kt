package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.common.Constants
import com.example.sampleapp.data.local.SampleDao
import com.example.sampleapp.data.local.SampleDatabase
import com.example.sampleapp.data.remote.SampleApi
import com.example.sampleapp.data.repository.SampleRepositoryImpl
import com.example.sampleapp.domain.repository.SampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, SampleDatabase::class.java, "SampleDB").build()


    @Singleton
    @Provides
    fun provideDao(
        database: SampleDatabase
    ) = database.sampleDao()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideConvertorFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
        return retrofit.build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SampleApi {
        return retrofit.create(SampleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSampleRepository(remote: SampleApi, local: SampleDao): SampleRepository {
        return SampleRepositoryImpl(remote, local)
    }
}