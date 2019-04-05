package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.microlabs.trallet.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Book

    @Insert
    fun insertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Query("UPDATE books SET title = :title, description = :desc WHERE id = :id")
    fun updateBookById(id: Int, title: String, desc: String)
}