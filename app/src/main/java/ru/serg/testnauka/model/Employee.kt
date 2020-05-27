package ru.serg.testnauka.model

import com.google.gson.annotations.Expose
import java.io.Serializable


data class Employee(
    val id: Int? = 0,
    var name: String? = null,
    var idNumber: Long? = null,
    var address: String? = null,
    @Expose(deserialize = false)
    var department: Department? = null
) : Serializable {
    constructor() : this(-1, "", -1, "")
}