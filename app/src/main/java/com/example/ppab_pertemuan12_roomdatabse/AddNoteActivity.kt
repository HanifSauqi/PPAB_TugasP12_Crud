package com.example.ppab_pertemuan12_roomdatabse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ppab_pertemuan12_roomdatabse.databinding.ActivityAddNoteBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddNoteActivity : AppCompatActivity() {

    private lateinit var executorService: ExecutorService
    private lateinit var mNoteDao: NoteDao
    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        setupSaveButton()
        setupCancelButton()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val price = binding.priceEditText.text.toString()

            // Validasi apakah input tidak kosong
            if (title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()) {
                executorService.execute {
                    // Simpan data ke dalam database
                    val note = Note(0,title = title,
                        description = description, price = price.toInt())
                    mNoteDao.insert(note)
                    finish()
                }
            } else {
                // Tampilkan toast jika ada input yang kosong
                showToast("Harap isi semua kolom")
            }
        }
    }

    private fun setupCancelButton() {
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}