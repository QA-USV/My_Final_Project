package ru.netology.fmhandroid.repository.noteRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.fmhandroid.dao.NoteDao
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.NoteStatus
import ru.netology.fmhandroid.entity.*
import ru.netology.fmhandroid.exceptions.*
import java.time.LocalDateTime

class NoteRepositoryImp(private val dao: NoteDao) : NoteRepository {
    override val data = dao.getAllNotes()
        .map(List<NoteEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAllNotes(): List<Note> {
        try {
            val listOfNotes = mutableListOf<Note>()
            listOfNotes.add(
                Note(
                    id = 1,
                    description = "Выполнение внутримышечной инъекции Мельдоний, милдронат",
                    planeExecuteDate = LocalDateTime.of(
                        2021,
                        6,
                        10,
                        20,
                        10
                    ),
                    factExecuteDate = null,
                    noteStatus = NoteStatus.ACTIVE,
                    shortPatientName = "Салтыков-Щедрин Е.В.",
                    shortExecutorName = "Североморская Е.В."
                )
            )

            listOfNotes.add(
                Note(
                    id = 2,
                    description = "Осмотр пациента.",
                    planeExecuteDate = LocalDateTime.of(
                        2021,
                        6,
                        10,
                        23,
                        10
                    ),
                    factExecuteDate = null,
                    noteStatus = NoteStatus.ACTIVE,
                    shortPatientName = "Ложкин П.В",
                    shortExecutorName = "Gregory House M.D."
                )
            )
            listOfNotes.add(
                Note(
                    id = 3,
                    description = "Восполнение водного баланса организма посредством капельцы с физ.раствором",
                    planeExecuteDate = LocalDateTime.of(
                        2021,
                        7,
                        10,
                        20,
                        10
                    ),
                    factExecuteDate = null,
                    noteStatus = NoteStatus.ACTIVE,
                    shortPatientName = "Североморская Е.В.",
                    shortExecutorName = "Аброськин Н.Г."
                )
            )
            dao.insert(listOfNotes.map { it.toEntity() })
            return listOfNotes

        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun saveNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Long): Note {
        TODO("Not yet implemented")
    }
}