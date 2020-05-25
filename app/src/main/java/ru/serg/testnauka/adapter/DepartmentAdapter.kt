package ru.serg.testnauka.adapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_department.view.*
import ru.serg.testnauka.R
import ru.serg.testnauka.model.Department


class DepartmentAdapter(val department: Department) : Item<ViewHolder>() {
    override fun getLayout(): Int {

        return R.layout.cardview_department
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_departmentName.text = department.name
        viewHolder.itemView.textView_numberOfEmpl.text = department.employee?.size.toString()
        viewHolder.itemView.textView_location.text = department.location
    }
}

