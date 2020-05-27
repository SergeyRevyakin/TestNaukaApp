package ru.serg.testnauka.model

data class BusinessCalendar(

    val id: Int? = 0,
    val date: String?, //LocalDate? = LocalDate.now(),
    var code: CalendarCode?=null,
    val employee: Employee
){


    override fun hashCode(): Int {
        var result = date?.hashCode() ?: 0
        result = 31 * result + employee.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BusinessCalendar

        if (date != other.date) return false
        if (employee != other.employee) return false

        return true
    }

}