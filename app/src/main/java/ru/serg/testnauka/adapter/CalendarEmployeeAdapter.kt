package ru.serg.testnauka.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.BusinessCalendar
import ru.serg.testnauka.model.CalendarCode

class CalendarEmployeeAdapter(
    val businessCalendarList: List<BusinessCalendar>,
    val codeList: ArrayList<CalendarCode>,
    val context: Context
) : RecyclerView.Adapter<CalendarEmployeeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(p0.context).inflate(R.layout.cardview_emp_daily, p0, false)
        return ViewHolder(viewHolder);
    }

    override fun getItemCount(): Int {
        return businessCalendarList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.name?.text = businessCalendarList[p1].employee.name
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            codeList
        ) //simple_dropdown_item_1line
        p0.spinner.adapter = adapter
        businessCalendarList[p1].code.id?.let { p0.spinner.setSelection(it) }
        p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                //Toast.makeText(context,"Pos $pos",Toast.LENGTH_SHORT).show()
                val calendar = businessCalendarList[p1]
                calendar.code=codeList[pos]
                GlobalScope.launch {
                    TestNaukaApi.invoke().putCalendar(calendar)
                }
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }

//            .setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(context,"Pos $position",Toast.LENGTH_SHORT).show()
//            val a = parent.selectedItem
//        }
        //p0.spinner.se
        //p0.count?.text = businessCalendarList[p1].count.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.textView_empName)
        val spinner = itemView.findViewById<Spinner>(R.id.spinner)
        //val count = itemView.findViewById<TextView>(R.id.tvCount)

    }
}

