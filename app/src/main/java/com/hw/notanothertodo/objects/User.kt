package com.hw.notanothertodo.objects

import android.icu.text.CaseMap.Title

class User (
    var name: String,
    val email: String
    )
{
    val currentTasks : HashMap<Int, Task> = HashMap()
    val archivedTasks: HashMap<Int, Task> = HashMap()
    private var currentPoints: Int = 0
    private var lifetimePoints: Int = 0
    private var nextTaskID: Int = 0
    var categories: HashMap<String, HashMap<String, Task>> = HashMap()

    fun startUp(){
        addCategory("default")
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
        currentTasks[task.id] = task
        nextTaskID++
        //to do: categories solution

    }

    fun archiveTask(task: Task){
        currentTasks.remove(task.id)
        archivedTasks[task.id] = task
        addPoints(task.pointValue)
    }


    fun deleteTask(task: Task){
        currentTasks.remove(task.id)
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

    fun addCategory(cat: String){
        if (categories.containsKey(cat)){
           return
        }
        val newMap = HashMap<String, Task>()
        categories[cat] = newMap
    }

    private fun removeCategory(cat: String){
        if (!categories.containsKey(cat)){
            return
        }
        categories.remove(cat)
    }


}