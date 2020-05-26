package ru.serg.testnauka.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.cardview_emp_daily.*
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.adapter.CalendarEmployeeAdapter
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.CalendarCode
import java.time.LocalDate

class CalendarActivity : AppCompatActivity() {

    private var date: LocalDate = LocalDate.now()
    private var businessCalendarList: List<BusinessCalendar>? = null
    private val calendarAdapter = GroupAdapter<ViewHolder>()
    private var calendarCodes: ArrayList<CalendarCode> = arrayListOf()

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
        getEmployeeList()



        val recyclerView: RecyclerView = findViewById(R.id.recycleview_calendar)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        businessCalendarList?.forEach {
//            calendarAdapter.add(CalendarEmployeeAdapter(it))
//        }

        recyclerView.adapter = CalendarEmployeeAdapter(businessCalendarList!!, calendarCodes,this)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = LocalDate.of(year, month + 1, dayOfMonth)
            getEmployeeList()
            Toast.makeText(this, "Its ${businessCalendarList?.size}", Toast.LENGTH_SHORT).show()
//            businessCalendarList?.forEach {
//                calendarAdapter.add(CalendarEmployeeAdapter(it))
//            }
//            recyclerView.adapter = calendarAdapter
            recyclerView.adapter = CalendarEmployeeAdapter(businessCalendarList!!, calendarCodes, this)
        }
    }

    private fun getCodes() {
        runBlocking {
            calendarCodes = TestNaukaApi.invoke().getCalendarCodes()
        }
        calendarCodes.add(0, CalendarCode(null,null))
    }

    private fun getEmployeeList() {
        runBlocking {
            businessCalendarList = TestNaukaApi.invoke().getCalendarDay(date)
        }
    }

}
