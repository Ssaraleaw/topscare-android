package com.android.topscare.di.modules

import com.android.topscare.domain.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object TopsCareModule {
    @Provides
    fun provideApi(@Named("BaseRetrofit") retrofit: Retrofit): Api =
        retrofit.create()
}