package com.example.labexam04.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databsehelper(context: Context): SQLiteOpenHelper(context,DB_Name,null,DB_version) {
  companion object{
      private  val DB_Name="task"
      private val DB_version= 1
      private val TABLE_NAME="tasklist"
      private val ID ="id"
      private val TASK_NAME="taskname"
      private  val TASK_DETAILS="taskdetails"

  }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "createtable $TABLE_NAME($ID Integer,$TASK_NAME Text,$TASK_DETAILS Text);"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}