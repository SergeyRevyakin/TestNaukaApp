package ru.serg.testnauka.adapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_employee.view.*
import ru.serg.testnauka.R
import ru.serg.testnauka.model.Employee

class EmployeeAdapter(val employee: Employee) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.cardview_employee
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_employeeName.text = employee.name
        viewHolder.itemView.textView_empDepName.text = employee.department?.name
        viewHolder.itemView.textView_numberOfEmplID.text = employee.idNumber.toString()
        viewHolder.itemView.textView_locationEmployee.text = employee.address
    }
}