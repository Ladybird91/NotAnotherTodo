package com.hw.notanothertodo.objects

import androidx.compose.runtime.mutableStateListOf


class User (
    var name: String,
    val email: String
    )
{
    val currentTasks = mutableStateListOf<Task>()
    val currentPrizes = mutableStateListOf<Prize>()
    val archivedTasks: MutableList<Task> = ArrayList()
    private var currentPoints: Int = 438
    private var lifetimePoints: Int = 0
    private var nextTaskID: Int = 0
    var categories: MutableList<Category> = ArrayList()


    fun startUp(){
        addCategory(Category("Default"))
        addCategory(Category("Chores"))
        addCategory(Category("Home"))
        addCategory(Category("Work"))
        addTask(Task("Task 1", categories[0], "High", "Hard", 23))
        addTask(Task("Task 2", categories[1], "Medium", "Moderate", 36))
        addTask(Task("Task 3", categories[2], "Low", "Hard", 10))
        addTask(Task("Task 4", categories[0], "Medium", "Easy", 26))
    }

    fun startUpPrize(){
        addPrize(Prize("IceScream scoop", "Cheap",47))
        addPrize(Prize("IceScream pint", "Affordable",112))
        addPrize(Prize("Buy Reformation dress", "Premium",589))
        addPrize(Prize("Buy new houseplant", "Mid-range",235))
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

    fun addPrize(prize: Prize){
        currentPrizes.add(prize)
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