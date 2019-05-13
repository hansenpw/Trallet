package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.microlabs.trallet.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

    @Query("UPDATE books SET title = :title, description = :desc WHERE id = :id")
    suspend fun updateBookById(id: Int, title: String, desc: String)
}