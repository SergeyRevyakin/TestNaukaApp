package ru.serg.testnauka.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.CalendarCode
import ru.serg.testnauka.model.Department
import ru.serg.testnauka.model.Employee
import java.time.LocalDate
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://10.0.2.2:8080/"

interface TestNaukaApi {
    @GET("/department/all")
    suspend fun getAllDepartments(): List<Department>

    @GET("/employee/all")
    suspend fun getAllEmployees(): List<Employee>

    @GET("employee/id={id}")
    suspend fun getEmployeeById(
        @Path("id") id: Int
    ): Employee?

    @POST("department/post")
    suspend fun postDepartment(@Body department: Department)

    @Headers("Content-Type: application/json")
    @PUT("department/put")
    suspend fun putDepartment(@Body department: Department)

    @DELETE("department/del={id}")
    suspend fun deleteDepartment(
        @Path("id") id:Int
    )

    @GET("/businesscalendar/date={date}")
    suspend fun getCalendarDay(
        @Path("date") date:LocalDate
    ): List<BusinessCalendar>?

    @PUT("/businesscalendar/put")
    suspend fun putCalendar(
        @Body businessCalendar: BusinessCalendar
    )

    @GET("/calendarcode/")
    suspend fun getCalendarCodes (): ArrayList<CalendarCode>

    companion object{
        operator fun invoke():TestNaukaApi{
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TestNaukaApi::class.java)
        }
    }
}