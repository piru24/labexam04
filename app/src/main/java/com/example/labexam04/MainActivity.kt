package com.example.labexam04

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var toDoList: ListView
    private lateinit var addItemEdit: EditText
    private lateinit var errorMessageText: TextView
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton = findViewById<Button>(R.id.add_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        toDoList = findViewById(R.id.to_do_list_view)
        addItemEdit = findViewById(R.id.add_item_edit)
        errorMessageText = findViewById(R.id.error_message_text)

        val listItems = arrayListOf<String>()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listItems)
        toDoList.adapter = arrayAdapter

        // Add item
        addButton.setOnClickListener {
            val itemText: String = addItemEdit.text.toString()
            if (itemText.isNotEmpty()) {
                listItems.add(itemText)
                arrayAdapter.notifyDataSetChanged()
                addItemEdit.setText("")
                errorMessageText.visibility = View.GONE
                Toast.makeText(this, "$itemText added", Toast.LENGTH_SHORT).show()
            } else {
                errorMessageText.visibility = View.VISIBLE
                errorMessageText.text = "Please write something..."
                Toast.makeText(this, "Please fill in the gap", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

        // Delete item
        deleteButton.setOnClickListener {
            val position: SparseBooleanArray = toDoList.checkedItemPositions
            val count = toDoList.count
            for (item in count - 1 downTo 0) {
                if (position.get(item)) {
                    arrayAdapter.remove(listItems[item])
                }
            }
            position.clear()
            arrayAdapter.notifyDataSetChanged()
        }

        // Edit item on click
        toDoList.setOnItemClickListener { _, _, position, _ ->
            val alertDialogBuilder = android.app.AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Edit Item")
            val input = EditText(this)
            input.setText(listItems[position])
            alertDialogBuilder.setView(input)
            alertDialogBuilder.setPositiveButton("Update") { dialog, _ ->
                val editedItem = input.text.toString()
                if (editedItem.isNotEmpty()) {
                    listItems[position] = editedItem
                    arrayAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please write something", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
