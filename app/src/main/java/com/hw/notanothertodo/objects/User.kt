package com.hw.notanothertodo.objects

import android.icu.text.CaseMap.Title

class User (
    var name: String,
    val email: String
    )
{
    val currentTasks : MutableList<Task> = ArrayList()
    val archivedTasks: MutableList<Task> = ArrayList()
    private var currentPoints: Int = 0
    private var lifetimePoints: Int = 0
    private var nextTaskID: Int = 0
    var categories: MutableList<Category> = ArrayList()

    fun startUp(){
        addCategory(Category("default"))
        addCategory(Category("chores"))
        addCategory(Category("home"))
        addCategory(Category("work"))
        addTask(Task("Task 1", categories[0], "High", "Hard", false))
        addTask(Task("Task 2", categories[1], "Medium", "Medium", false))
        addTask(Task("Task 3", categories[2], "Low", "Hard", false))
        addTask(Task("Task 4", categories[0], "Medium", "Easy", false))
    }

    fun getLifetimePoints(): Int {
        return lifetimePoints
    }

    fun getCurrentPoints(): Int {
        return currentPoints
    }

    fun numCurrentTasks(): Int {
        return currentTasks.size
    }

    fun numTotalTasks(): Int {
        return currentTasks.size + archivedTasks.size
    }

    fun getNextTaskID(): Int {
        return nextTaskID
    }
    fun addTask(task: Task){
        currentTasks.add(task)
        //to do: categories solution

    }

    fun archiveTask(task: Task){
        currentTasks.remove(task)
        archivedTasks.add(task)
    }


    fun deleteTask(task: Task){
        currentTasks.remove(task)
    }

    private fun minusCurrPoints(points: Int){
        currentPoints -= points
    }
    fun minusPoints(points: Int){
        currentPoints -= points
        lifetimePoints -= points
    }

    fun addPoints(points: Int){
        currentPoints += points
        lifetimePoints += points
    }


    fun redeemPrize(prize: Prize){
        minusCurrPoints(prize.cost)
    }

    fun addCategory(cat: Category){
        if (categories.contains(cat)){
           return
        }
        val newMap = HashMap<String, Task>()
        categories.add(cat)
    }

    private fun removeCategory(cat: Category){
        if (!categories.contains(cat)){
            return
        }
        categories.remove(cat)
    }


}