package com.android.topscare.di.modules

import android.app.Application
import android.content.Context
import com.android.topscare.BuildConfig
import com.android.topscare.lib_base.utils.HttpLogger
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = createGsonConverter()


    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): okhttp3.Cache =
        okhttp3.Cache(application.cacheDir, 10L * 1024L * 1024L)


    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(HttpLogger())
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    @Provides
    @Singleton
    @Named("BaseOkHttpClient")
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        cache: Cache,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .cache(cache)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(ChuckerInterceptor(context))
        }
        return builder.build()
    }

    private fun createGsonConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    fun provideRetrofit(
        gson: GsonConverterFactory,
        @Named("BaseOkHttpClient") okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(gson)
        .baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .build()
}