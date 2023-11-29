import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppab_pertemuan12_roomdatabse.Note
import com.example.ppab_pertemuan12_roomdatabse.NoteDao
import com.example.ppab_pertemuan12_roomdatabse.R
import com.example.ppab_pertemuan12_roomdatabse.UpdateNoteActivity2
import java.util.concurrent.ExecutorService

class NoteAdapter(
    private val notes: List<Note>,
    private val noteDao: NoteDao,
    private val executorService: ExecutorService
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.description
        holder.priceTextView.text = "Rp ${note.price}"

        // Implementasi aksi klik untuk update dan delete
        holder.updateButton.setOnClickListener {
            // Tambahkan logika untuk tombol update di sini
            val note = notes[position]
            val intent = Intent(holder.itemView.context, UpdateNoteActivity2::class.java)
            intent.putExtra("note_id", note.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            // Tambahkan logika untuk tombol delete di sini
            // Gunakan NoteDao untuk menghapus catatan
            executorService.execute {
                noteDao.delete(note)
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
