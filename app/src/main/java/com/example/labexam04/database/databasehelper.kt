package com.example.labexam04.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.labexam04.model.TaskListModel
import java.lang.Integer.*

class databasehelper (context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {
    companion object {
        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val ID = "id"
        private val TABLE_NAME = "tasklist"
        private val TASK_NAME = "tasklist"
        private val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE  $TABLE_NAME($ID INTEGER PRIMARY KEY,$TASK_NAME TEXT,$TASK_DETAILS TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)

    }

    @SuppressLint("Range")
    fun getAllTask(): List<TaskListModel> {
        val tasklist = ArrayList<TaskListModel>()
        val db = readableDatabase // Use readableDatabase since you're only reading data
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            while (cursor.moveToNext()) {
                val task = TaskListModel()
                task.id = cursor.getInt(cursor.getColumnIndex(ID))
                task.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                task.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                tasklist.add(task)
            }
        }

        cursor.close()
        return tasklist
    }


    //insert
    fun addTask(task: TaskListModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, task.name)
        values.put(TASK_DETAILS, task.details)

        val insertedId = db.insert(TABLE_NAME, null, values)
        db.close()

        return insertedId != -1L // Check if insertion was successful
    }
// SELECT THE DATA FROM THE PARTICULAR ID
@SuppressLint("Range")
fun getTask(_id:Int):TaskListModel {
    val task = TaskListModel()
    val db = writableDatabase
    val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID= $_id"
    val cursor = db.rawQuery(selectQuery, null)

    cursor?.moveToFirst()
    task.id = cursor.getInt(cursor.getColumnIndex(ID))
    task.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
    task.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))

    cursor.close()
    return task

}

    fun deleteTask(_id: Int):Boolean{
        val db= this.writableDatabase
        val insertedId =db.delete(TABLE_NAME, ID+"=?", arrayOf(_id.toString())).toLong()

        db.close()

        return insertedId != -1L // Check if insertion was successful
    }
    fun updateTask(task: TaskListModel):Boolean{
        val db =this.writableDatabase
        val values =ContentValues()
        values.put(TASK_NAME, task.name)
        values.put(TASK_DETAILS, task.details)

        val insertedId = db.update(TABLE_NAME,values, ID+"=?", arrayOf(task.id.toString())).toLong()
        db.close()

        return insertedId != -1L // Check if insertion was successful
    }

}