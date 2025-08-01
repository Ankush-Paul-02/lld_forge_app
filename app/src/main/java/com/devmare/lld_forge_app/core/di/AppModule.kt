package com.devmare.lld_forge_app.core.di

import android.content.Context
import com.devmare.lld_forge_app.core.interceptor.AuthInterceptor
import com.devmare.lld_forge_app.core.interceptor.TokenProvider
import com.devmare.lld_forge_app.core.interceptor.TokenProviderImpl
import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import com.devmare.lld_forge_app.data.api.AuthApi
import com.devmare.lld_forge_app.data.api.UserApi
import com.devmare.lld_forge_app.data.repository.AuthRepositoryImpl
import com.devmare.lld_forge_app.data.repository.UserRepositoryImpl
import com.devmare.lld_forge_app.domain.repository.AuthRepository
import com.devmare.lld_forge_app.domain.repository.UserRepository
import com.devmare.lld_forge_app.domain.usecase.LoginUseCase
import com.devmare.lld_forge_app.domain.usecase.SignUpUseCase
import com.devmare.lld_forge_app.domain.usecase.UserUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Provides
    @Singleton
    fun provideTokenProvider(dataStoreManager: DataStoreManager): TokenProvider {
        return TokenProviderImpl(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor {
        return AuthInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.29.239:8081/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    fun provideUserRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideUserUseCase(repository: UserRepository): UserUsecase {
        return UserUsecase(repository)
    }
}