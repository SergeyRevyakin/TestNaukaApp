package ru.serg.testnauka.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.CalendarCode
import ru.serg.testnauka.model.Employee
import java.time.LocalDate


class CalendarEmployeeAdapter(
    val businessCalendarList: MutableList<BusinessCalendar>,
    val codeList: ArrayList<CalendarCode>,
    val date: LocalDate,
    val context: Context
) : RecyclerView.Adapter<CalendarEmployeeAdapter.ViewHolder>() {
    // @Inject
    lateinit var employeeList: List<Employee>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        //employeeList = DaggerDependencyComponent.create().em
        val viewHolder =
            LayoutInflater.from(p0.context).inflate(R.layout.cardview_emp_daily, p0, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return businessCalendarList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        if (businessCalendarList.isEmpty()) {
//            getEmpl()
//            employeeList.forEach{
//                businessCalendarList.add(BusinessCalendar(date = date.toString(), employee = it))
//            }
//            //businessCalendarList = listOf(BusinessCalendar(date = date.toString(), employee = )
//        }
        p0.name?.text = businessCalendarList[p1].employee.name
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            codeList
        )
        p0.spinner.adapter = adapter
        p0.spinner.setSelection(businessCalendarList[p1].code!!.id!!)
        //val calendar = businessCalendarList[p1].copy()
        p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val calendar = businessCalendarList[p1].copy()
                    //BusinessCalendar(date = businessCalendarList[p1].date, code = businessCalendarList[p1].code, employee = businessCalendarList[p1].employee)
                    // Gson().fromJson(Gson().toJson(businessCalendarList[p1]), BusinessCalendar::class.java)// businessCalendarList[p1]
                calendar.code = codeList[pos]
                if (calendar.code!!.id != 0 &&
                    calendar.code!!.id != businessCalendarList[p1].code!!.id
                ) {
                    GlobalScope.launch {
                        TestNaukaApi.invoke().putCalendar(calendar)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {}

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.textView_empName)
        val spinner = itemView.findViewById<Spinner>(R.id.spinner)
    }

    fun getEmpl() {
        runBlocking {
            employeeList = TestNaukaApi.invoke().getAllEmployees()
        }

    }
}

