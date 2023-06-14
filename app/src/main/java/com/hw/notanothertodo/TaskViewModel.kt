package com.hw.notanothertodo

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.hw.notanothertodo.objects.Category

class TaskViewModel : ViewModel() {

    val selectedTask = mutableStateMapOf<String, Boolean>()

    fun addList(categories: List<Category>) {
        if (selectedTask.isEmpty()) {
            selectedTask.putAll(categories
                .associate { it.name to true })
            Log.d("addList", "NEW LIST")
        }
    }
}