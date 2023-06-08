package com.hw.notanothertodo.objects

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class User (
    var name: String,
    val email: String
    )
{
    val currentTasks = mutableStateListOf<Task>()
    val currentPrizes = mutableStateListOf<Prize>()
    val archivedTasks: MutableList<Task> = ArrayList()
    // Adding int values to these variables for presentation demo
    private var currentPoints: Int = 438
    private var lifetimePoints: Int = 1574
    private var taskCompleted: Int = 34
    private var nextTaskID: Int = 0
    var categories: MutableList<Category> = ArrayList()


    fun startUp(){
        addCategory(Category("Personal"))
        addCategory(Category("Chores"))
        addCategory(Category("School"))
        addCategory(Category("Work"))
        addTask(Task("Dishes", categories[1], "Medium", "Easy", 23))
        addTask(Task("Groceries", categories[0], "High", "Moderate", 42))
        addTask(Task("Water plants", categories[1], "Medium", "Easy", 18))
        addTask(Task("Laundry", categories[1], "Low", "Easy", 10))
        addTask(Task("Fold laundry", categories[1], "Low", "Easy", 14))
        addTask(Task("Workout", categories[0], "Medium", "Hard", 40))
        addTask(Task("Read emails", categories[3], "Medium", "Easy", 26))
        addTask(Task("Call Andrea", categories[0], "Medium", "Easy", 24))
        addTask(Task("Finish assignment 7", categories[2], "Medium", "Moderate", 37))
        addTask(Task("Mid-term test", categories[2], "High", "Hard", 56))
        addTask(Task("Complete project requirements", categories[3], "Medium", "Moderate", 39))

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

    fun getLifetimeTasksCompleted(): Int {
        return taskCompleted
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

class UserViewModel : ViewModel() {
    val user: User = User("jason", "jason@hotmail.com")

    init {
        user.startUp()
        user.startUpPrize()
    }
}