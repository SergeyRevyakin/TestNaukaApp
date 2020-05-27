package ru.serg.testnauka.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.adapter.CalendarEmployeeAdapter
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.CalendarCode
import ru.serg.testnauka.model.Employee
import java.time.LocalDate

//@Module
class CalendarActivity : AppCompatActivity() {

    private var date: LocalDate = LocalDate.now()
    private lateinit var businessCalendarList: MutableList<BusinessCalendar>
    private var calendarCodes: ArrayList<CalendarCode> = arrayListOf()
    lateinit var employeeList: List<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        getCodes()
        getBusinessDayList()
        getEmployeeList()


        val recyclerView: RecyclerView = findViewById(R.id.recycleview_calendar)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        businessCalendarList?.forEach {
//            calendarAdapter.add(CalendarEmployeeAdapter(it))
//        }

        recyclerView.adapter = CalendarEmployeeAdapter(
            businessCalendarList, calendarCodes,
            LocalDate.now(), this
        )

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = LocalDate.of(year, month + 1, dayOfMonth)
            getBusinessDayList()
            //Toast.makeText(this, "Its ${businessCalendarList.size}", Toast.LENGTH_SHORT).show()
            if (businessCalendarList.size < employeeList.size) {
                employeeList.forEach {
                    //if (businessCalendarList.)
                    if (!businessCalendarList.contains(
                            BusinessCalendar(
                                date = date.toString(),
                                employee = it
                            )
                        )
                    ) {
                        businessCalendarList.add(
                            BusinessCalendar(
                                date = date.toString(),
                                employee = it,
                                code = calendarCodes[0]
                            )
                        )
                    }
                }
                //businessCalendarList = listOf(BusinessCalendar(date = date.toString(), employee = )
            }
            recyclerView.adapter =
                CalendarEmployeeAdapter(businessCalendarList, calendarCodes, date, this)
        }
    }

    private fun getCodes() {
        runBlocking {
            calendarCodes = TestNaukaApi.invoke().getCalendarCodes()
        }
        calendarCodes.add(0, CalendarCode("", ""))
    }

    private fun getBusinessDayList() {
        runBlocking {
            businessCalendarList = TestNaukaApi.invoke().getCalendarDay(date)?.toMutableList()!!
        }
    }

    //@Provides
    private fun getEmployeeList() {

        runBlocking {
            employeeList = TestNaukaApi.invoke().getAllEmployees()
        }

    }

}
