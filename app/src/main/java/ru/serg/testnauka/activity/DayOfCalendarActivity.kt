package ru.serg.testnauka.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_day_of_calendar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.Employee

class DayOfCalendarActivity : AppCompatActivity() {

    private var date: String? = null
    private var listOfDay: List<BusinessCalendar>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_of_calendar)

        date = intent.getStringExtra("date")

        getEmployeeList()

        //textView3.text = listOfDay?.size.toString()//get(1)?.employee?.name ?: "LOL"
    }

    fun getEmployeeList(){
        if (!date.isNullOrEmpty()) {
            runBlocking {
                listOfDay = TestNaukaApi.invoke().getCalendarDay(date!!)
            }
        }
    }
}
