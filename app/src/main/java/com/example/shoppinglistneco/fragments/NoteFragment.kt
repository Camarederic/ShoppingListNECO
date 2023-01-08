package com.example.shoppinglistneco.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.activities.MainApp
import com.example.shoppinglistneco.activities.NewNoteActivity
import com.example.shoppinglistneco.database.MainViewModel
import com.example.shoppinglistneco.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance() = NoteFragment()
    }

    override fun onClickNew() {
        startActivity(Intent(activity, NewNoteActivity::class.java))
    }

}