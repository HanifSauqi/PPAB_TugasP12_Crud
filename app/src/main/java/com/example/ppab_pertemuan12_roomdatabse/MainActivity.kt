package com.example.ppab_pertemuan12_roomdatabse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.ppab_pertemuan12_roomdatabse.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mediator: TabLayoutMediator
    lateinit var viewPager2: ViewPager2
    private lateinit var executorService: ExecutorService
    private lateinit var mNoteDao:NoteDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            viewPager2 = viewPager
            viewPager.adapter = TabAdapter(supportFragmentManager, this@MainActivity.lifecycle)
            mediator = TabLayoutMediator(tabLayout, viewPager)
            { tab, position ->
                when (position) {
                    0 -> tab.text = "Pemasukan"
                    1 -> tab.text = "Pengeluaran"
                }
            }
            mediator.attach()

        }
    }
}