package com.example.labexam04.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.AddTask
import com.example.labexam04.R
import com.example.labexam04.model.TaskListModel


class TaskListAdapter(tasklist:List<TaskListModel>,internal var context: Context)
    :RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {


        internal  var tasklist :List<TaskListModel> = ArrayList()
    init {
        this.tasklist=tasklist
    }
        inner class TaskViewHolder(view:View):RecyclerView.ViewHolder(view)
        {
            var name :TextView= view.findViewById(R.id.txt_name)
            var details:TextView=view.findViewById(R.id.txt_details)
            var btn_edit:Button=view.findViewById(R.id.btn_edit)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
       val view=LayoutInflater.from(context).inflate(R.layout.recycler_task_list,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasklist[position]
        holder.name.text = task.name
        holder.details.text = task.details

        // Setup click listener for the edit button
        holder.btn_edit.setOnClickListener {
            // Create an intent to start the addTask activity
            val i = Intent(context, AddTask::class.java)

            // Pass any necessary data to the AddTask activity (if needed)
            i.putExtra("Mode", "E")
            i.putExtra("Id",task.id)

            // Start the activity using the context provided
            context.startActivity(i)
        }
    }

}