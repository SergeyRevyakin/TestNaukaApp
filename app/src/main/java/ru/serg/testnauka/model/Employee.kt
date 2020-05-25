package ru.serg.testnauka.model

import java.io.Serializable


data class Employee(
    val id: Int? = 0,
    val name: String?,
    val idNumber: Long?,
    val address: String?,
    val department: Department? = null
): Serializable{
    constructor():this(-1,"",-1,"")
}