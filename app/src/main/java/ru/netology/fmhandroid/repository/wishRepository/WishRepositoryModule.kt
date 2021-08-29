package ru.netology.fmhandroid.repository.wishRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class WishRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWishRepository(imp: WishRepositoryImp): WishRepository
}
