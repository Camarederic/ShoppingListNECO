package com.example.shoppinglistneco.database

import androidx.lifecycle.*
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.entities.ShopListItem
import com.example.shoppinglistneco.entities.ShopListNameItem
import kotlinx.coroutines.launch

class MainViewModel(database: MainDatabase) : ViewModel() {

    private val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allItemShopListNames: LiveData<List<ShopListNameItem>> =
        dao.getAllShopListNames().asLiveData()

    fun getAllItemsFromList(listId: Int): LiveData<List<ShopListItem>> {
        return dao.getAllShopListItems(listId).asLiveData()
    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteShopList(id: Int, deleteList: Boolean) = viewModelScope.launch {
        if (deleteList) dao.deleteShopListName(id)
        dao.deleteShopItemsByListId(id)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateShopListName(shopListNameItem: ShopListNameItem) = viewModelScope.launch {
        dao.updateShopListName(shopListNameItem)
    }

    fun updateListItem(item: ShopListItem) = viewModelScope.launch {
        dao.updateListItem(item)
    }


    @Suppress("UNCHECKED_CAST")
    class MainViewModelFactory(private val database: MainDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(database) as T
            }
            throw java.lang.IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}