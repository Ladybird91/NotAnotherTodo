package com.hw.notanothertodo.objects

class User (
    var name: String,
    val email: String,
    var currentPoints: Int,
    var lifetimePoints: Int,
    val incompleteTasks: Array<Task>,
    val allTasks: Array<Task>,
    val categories: Array<String>)
{
    //addActiveTask

    //archiveTask

    //deleteTask

    //minusCurrPoints

    //minusLifetimePoints

    //addPoints

    //redeemPrize


}