package ru.serg.testnauka.model

data class CalendarCode(
    val code: String?,
    val description: String?,
    val id: Int? = 0
){
    override fun toString(): String {
        return "$code   $description"
    }
}