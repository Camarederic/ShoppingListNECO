package com.example.shoppinglistneco.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityNewNoteBinding
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.fragments.NoteFragment
import com.example.shoppinglistneco.utils.HtmlManager
import com.example.shoppinglistneco.utils.MyTouchListener
import com.example.shoppinglistneco.utils.TimeManager
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    private var pref:SharedPreferences? = null
    private lateinit var defPref:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        setContentView(binding.root)

        actionBarSettings()

        getNote()

        init()

        setTextSize()

        onClickColorPicker()

        actionMenuCallback()

    }

    private fun onClickColorPicker() {
        binding.imageButtonRed.setOnClickListener {
            setColorForSelectedText(R.color.picker_red)
        }
        binding.imageButtonGreen.setOnClickListener {
            setColorForSelectedText(R.color.picker_green)
        }
        binding.imageButtonOrange.setOnClickListener {
            setColorForSelectedText(R.color.picker_orange)
        }
        binding.imageButtonYellow.setOnClickListener {
            setColorForSelectedText(R.color.picker_yellow)
        }
        binding.imageButtonBlue.setOnClickListener {
            setColorForSelectedText(R.color.picker_blue)
        }
        binding.imageButtonBlack.setOnClickListener {
            setColorForSelectedText(R.color.picker_black)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
        pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }

    }

    private fun fillNote() {
        binding.edTitle.setText(note?.title)
        binding.edDescription.setText(HtmlManager.getTextFromHtml(note?.content!!).trim())

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
        } else if (item.itemId == R.id.bold) {
            setBoldForSelectedText()
        } else if (item.itemId == R.id.color_picker)
            if (binding.colorPicker.isShown) {
                closeColorPicker()
            } else {
                openColorPicker()
            }
        return super.onOptionsItemSelected(item)
    }

    // функция для выделения слова и изменения его на другой шрифт
    private fun setBoldForSelectedText() {
        val startPosition = binding.edDescription.selectionStart
        val endPosition = binding.edDescription.selectionEnd

        val styles =
            binding.edDescription.text.getSpans(startPosition, endPosition, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
            binding.edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }

        binding.edDescription.text.setSpan(
            boldStyle,
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.edDescription.text.trim()
        binding.edDescription.setSelection(startPosition)
    }

    // Устанавливаем цвет для выбранного текста
    private fun setColorForSelectedText(colorId: Int) {
        val startPosition = binding.edDescription.selectionStart
        val endPosition = binding.edDescription.selectionEnd

        val styles = binding.edDescription.text.getSpans(
            startPosition,
            endPosition,
            ForegroundColorSpan::class.java
        )

        if (styles.isNotEmpty())
            binding.edDescription.text.removeSpan(styles[0])


        binding.edDescription.text.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.edDescription.text.trim()
        binding.edDescription.setSelection(startPosition)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem?
        if (note == null) {
            tempNote = createNewNote()
        } else {
            editState = "update"
            tempNote = updateNote()
        }
        val intent = Intent()
        intent.putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
        intent.putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun updateNote(): NoteItem? {
        return note?.copy(
            title = binding.edTitle.text.toString(),
            content = HtmlManager.textToHtml(binding.edDescription.text)
        )
    }


    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.textToHtml(binding.edDescription.text),
            TimeManager.getCurrentTime(),
            ""
        )
    }

    // метод для стрелки назад на актион баре
    private fun actionBarSettings() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Создаем функцию для открытия Color Picker
    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    // Создаем функцию для закрытия Color Picker
    private fun closeColorPicker() {
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        binding.colorPicker.startAnimation(openAnim)
    }

    // Создаем функцию, которая убирает всплывающее меню, когда выбираем какое-то слово
    private fun actionMenuCallback() {
        val actionCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }
        }
        binding.edDescription.customSelectionActionModeCallback = actionCallback
    }

    private fun setTextSize(){
        binding.edTitle.setTextSize(pref?.getString("title_size_key", "16"))
        binding.edDescription.setTextSize(pref?.getString("content_size_key", "14"))
    }

    private fun EditText.setTextSize(size: String?) {
        if (size != null) {
            this.textSize = size.toFloat()
        }
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "blue") == "blue") {
            R.style.Theme_NewNoteBlue
        } else {
            R.style.Theme_NewNoteRed
        }
    }
}