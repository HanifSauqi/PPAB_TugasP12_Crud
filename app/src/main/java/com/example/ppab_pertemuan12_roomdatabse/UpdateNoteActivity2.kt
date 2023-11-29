package com.example.ppab_pertemuan12_roomdatabse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ppab_pertemuan12_roomdatabse.databinding.ActivityUpdateNote2Binding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UpdateNoteActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNote2Binding
    private lateinit var executorService: ExecutorService
    private lateinit var mNoteDao: NoteDao
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNote2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        // Mendapatkan ID catatan dari intent
        noteId = intent.getIntExtra("note_id", -1)

        if (noteId != -1) {
            // Mendapatkan catatan dari database berdasarkan ID
            executorService.execute {
                val note = mNoteDao.getNoteById(noteId)
                runOnUiThread {
                    // Menetapkan nilai awal pada EditText sesuai dengan data yang ada
                    binding.titleEditText.setText(note?.title)
                    binding.descriptionEditText.setText(note?.description)
                    binding.priceEditText.setText(note?.price.toString())
                }
            }
        }

        binding.btnSave.setOnClickListener {
            updateNote()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun updateNote() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val priceText = binding.priceEditText.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty() && priceText.isNotEmpty()) {
            val price = priceText.toDouble()

            executorService.execute {
                // Mendapatkan catatan dari database berdasarkan ID
                val note = mNoteDao.getNoteById(noteId)

                // Memperbarui nilai deskripsi dan harga
                note?.let {
                    it.description = description
                    it.price = price.toInt()

                    // Memperbarui catatan di database
                    mNoteDao.update(it)
                }

                runOnUiThread {
                    finish() // Menutup activity setelah berhasil memperbarui
                }
            }
        }
    }
}
