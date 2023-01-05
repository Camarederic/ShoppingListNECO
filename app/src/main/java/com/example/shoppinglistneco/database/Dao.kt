package com.example.shoppinglistneco.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglistneco.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Insert
    suspend fun insertNote(note:NoteItem)
}