package com.example.shoppinglistneco.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityNewNoteBinding
import com.example.shoppinglistneco.fragments.NoteFragment

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult() {
        val intent = Intent()
        intent.putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
        intent.putExtra(NoteFragment.DESCRIPTION_KEY, binding.edDescription.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }


    // метод для стрелки назад на актион баре
    private fun actionBarSettings() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}