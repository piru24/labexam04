package com.example.labexam04

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.adapter.TaskListAdapter
import com.example.labexam04.database.databasehelper
import com.example.labexam04.model.TaskListModel

class MainActivity : AppCompatActivity() {

    lateinit var recycler_task: RecyclerView
    lateinit var btn_add: Button
    var taskListAdapter: TaskListAdapter? = null
    var dbHandler: databasehelper? = null
    var tasklist: List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager: LinearLayoutManager? = null // Corrected declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recycler_task = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.btn_add_item)

        dbHandler = databasehelper(this)
        fetchList()

        btn_add.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }

    private fun fetchList() {
        linearLayoutManager = LinearLayoutManager(this) // Initialize LinearLayoutManager
        tasklist = dbHandler!!.getAllTask()
        taskListAdapter = TaskListAdapter(tasklist, applicationContext)
        recycler_task.layoutManager = linearLayoutManager // Set LinearLayoutManager to RecyclerView
        recycler_task.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}
