package ru.serg.testnauka.model

import java.time.LocalDate

data class BusinessCalendar(

    val id: Int? = 0,
    val date: String?, //LocalDate? = LocalDate.now(),
    val code: CalendarCode,
    val employee: Employee
)