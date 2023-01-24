package com.example.shoppinglistneco.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityNewNoteBinding
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.fragments.NoteFragment
import com.example.shoppinglistneco.utils.HtmlManager
import com.example.shoppinglistneco.utils.MyTouchListener
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarSettings()

        getNote()

        init()

        onClickColorPicker()

        actionMenuCallback()

    }

    private fun onClickColorPicker(){
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

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.textToHtml(binding.edDescription.text),
            getCurrentTime(),
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
    private fun actionMenuCallback(){
        val actionCallback = object : ActionMode.Callback{
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
}