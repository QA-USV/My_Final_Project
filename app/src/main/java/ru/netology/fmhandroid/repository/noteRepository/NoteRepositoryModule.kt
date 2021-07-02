package ru.netology.fmhandroid.repository.noteRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NoteRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPostRepository(imp: NoteRepositoryImp): NoteRepository
}