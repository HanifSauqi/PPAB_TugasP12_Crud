package com.example.ppab_pertemuan12_roomdatabse

import NoteAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ppab_pertemuan12_roomdatabse.databinding.FragmentPemasukanBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PemasukanFragment : Fragment() {
    private lateinit var noteDao: NoteDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: FragmentPemasukanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPemasukanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi noteDao dan executorService di sini
        val db = NoteRoomDatabase.getDatabase(requireContext())
        noteDao = db!!.noteDao()!!
        executorService = Executors.newSingleThreadExecutor()

        // Ambil semua catatan dari database menggunakan LiveData
        noteDao.allNotePemasukan.observe(viewLifecycleOwner, Observer { allNotes ->
            // Jalankan di thread utama
            // Inisialisasi RecyclerView dan adapter
            val noteAdapter = NoteAdapter(allNotes, noteDao, executorService)
            binding.notesRecyclerView.adapter = noteAdapter
            binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        })

        // Implementasi aksi klik untuk tombol "Tambah"
        binding.addButton.setOnClickListener {
            // Navigasi ke AddNoteActivity
            val intent = Intent(requireActivity(), AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
