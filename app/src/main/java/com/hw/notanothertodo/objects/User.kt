package com.hw.notanothertodo.objects

class User (
    var name: String,
    val email: String,
    private var currentPoints: Int,
    private var lifetimePoints: Int,
    private val incompleteTasks: HashMap<String, Task>,
    private val allTasks: HashMap<String, Task>,
    val categories: HashMap<String, Array<Task>>) //idt this implementation will work.
{
    private fun addTask(task: Task){
        val taskKey = task.title
        allTasks[taskKey] = task
        incompleteTasks[taskKey] = task
        //to do: categories solution

    }

    private fun archiveTask(task: Task){
        incompleteTasks.remove(task.title)
    }


    private fun deleteTask(task: Task){
        incompleteTasks.remove(task.title)
        allTasks.remove(task.title)
    }

    private fun minusCurrPoints(points: Int){
        currentPoints -= points
    }
    private fun minusPoints(points: Int){
        currentPoints -= points
        lifetimePoints -= points
    }

    private fun addPoints(points: Int){
        currentPoints += points
        lifetimePoints += points
    }


    private fun redeemPrize(prize: Prize){
        minusCurrPoints(prize.cost)
    }


}