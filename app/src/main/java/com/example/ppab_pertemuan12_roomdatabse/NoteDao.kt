package com.example.ppab_pertemuan12_roomdatabse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    // Fungsi untuk mengambil catatan berdasarkan judulnya
    @Query("SELECT * FROM note_table WHERE title = :title")
    fun getNotesByTitle(title: String): List<Note>

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    fun getNoteById(noteId: Int): Note

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @get:Query("Select * from note_table ORDER BY id ASC")
    val allNote: LiveData<List<Note>>

    @get:Query("Select * from note_table WHERE title = 'pemasukan' ORDER BY id ASC")
    val allNotePemasukan: LiveData<List<Note>>

    @get:Query("Select * from note_table WHERE title = 'pengeluaran' ORDER BY id ASC")
    val allNotePengeluaran: LiveData<List<Note>>


}