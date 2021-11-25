package ru.netology.fmhandroid.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.fmhandroid.db.AppDb

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideClaimDao(db: AppDb): ClaimDao = db.getClaimDao()

    @Provides
    fun provideUserDao(db: AppDb): UserDao = db.getUserDao()

    @Provides
    fun provideNewsDao(db: AppDb): NewsDao = db.getNewsDao()

    @Provides
    fun provideNewsCategoryDao(db: AppDb): NewsCategoryDao = db.getNewsCategoryDao()

    @Provides
    fun provideClaimCommentDao(db: AppDb): ClaimCommentDao = db.getClaimCommentDao()
}