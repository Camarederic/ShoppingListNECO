package com.example.shoppinglistneco.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.activities.MainApp
import com.example.shoppinglistneco.activities.NewNoteActivity
import com.example.shoppinglistneco.database.MainViewModel
import com.example.shoppinglistneco.database.NoteAdapter
import com.example.shoppinglistneco.databinding.FragmentNoteBinding
import com.example.shoppinglistneco.entities.NoteItem

class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onEditResult()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observer()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter()
        recyclerViewNote.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d("MyLog", "title: ${it.data?.getStringExtra(NEW_NOTE_KEY)}")
                mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
            }
        }
    }

    companion object {
        const val NEW_NOTE_KEY = "title_key"

        fun newInstance() = NoteFragment()
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

}