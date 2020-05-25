package ru.serg.testnauka.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Department(

    val id: Int?=0,
    var name: String? = null,
    var location: String? = null,
    @Expose(deserialize = false)
    var employee: List<Employee>? = arrayListOf()
)
    : Serializable {
    constructor() : this(-1,"","", null)
}