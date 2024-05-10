package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.labexam04.database.databasehelper
import com.example.labexam04.model.TaskListModel

class AddTask : AppCompatActivity() {

    lateinit var btn_save: Button
    lateinit var btn_delete: Button
    lateinit var ed_name: EditText
    lateinit var ed_details: EditText
    var dbhandler: databasehelper? = null
    var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        btn_save = findViewById(R.id.btn_save)
        btn_delete = findViewById(R.id.btn_delete)
        ed_name = findViewById(R.id.ed_name)
        ed_details = findViewById(R.id.ed_details)

        dbhandler = databasehelper(this)

        if (intent != null && intent.getStringExtra("mode") == "E") {
            // Update mode
            isEditMode = true
            btn_save.text = "Update Data"
            btn_delete.visibility = View.VISIBLE

            val taskId = intent.getIntExtra("ID", 0)
            val task = dbhandler?.getTask(taskId)
            if (task != null) {
                ed_name.setText(task.name)
                ed_details.setText(task.details)
            }

        } else {
            // Insert mode
            isEditMode = false
            btn_save.text = "Save Data"
            btn_delete.visibility = View.GONE
        }

        btn_save.setOnClickListener {
            val task = TaskListModel().apply {
                name = ed_name.text.toString()
                details = ed_details.text.toString()
                id = intent.getIntExtra("ID", 0)
            }

            val insertedId = if (isEditMode) {
                // Update task
                dbhandler?.updateTask(task)
            } else {
                // Insert task
                dbhandler?.addTask(task)
            }

            if (insertedId == true) {
                // Success: Navigate back to MainActivity
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                // Error handling
                Toast.makeText(applicationContext, "Some error occurred", Toast.LENGTH_LONG).show()
            }
        }

        btn_delete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes") { dialog, _ ->
                    val taskId = intent.getIntExtra("ID", 0)
                    val deleted = dbhandler?.deleteTask(taskId)
                    if (deleted == true) {
                        // Task deleted successfully
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Failed to delete task", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
