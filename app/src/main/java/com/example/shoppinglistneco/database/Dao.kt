package com.example.shoppinglistneco.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertShopListName(name: ShopListNameItem)

    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Query("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateShopListName(shopListNameItem: ShopListNameItem)
}