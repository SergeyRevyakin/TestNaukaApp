package ru.serg.testnauka.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_create_employee.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Department
import ru.serg.testnauka.model.Employee

class CreateEmployeeActivity : AppCompatActivity() {

    private lateinit var departmentList: MutableList<Department>
    private lateinit var department: Department
    private var employeeIntent: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_employee)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        employeeIntent = intent?.getSerializableExtra("employee") as Employee?

        getDepartments()

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            departmentList
        )
        spinnerEmployee.adapter = spinnerAdapter

        if (employeeIntent!=null) {
            fillFields()
        }

        spinnerEmployee.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                department = departmentList[position]
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.save_new_button -> {
            if (editText_employeeName.text.isNotBlank() &&
                editText_employeeId.text.isNotBlank() &&
                editText_employeeLocation.text.isNotBlank() &&
                department.id != null
            ) {
                val employee = Employee(
                    name = editText_employeeName.text.toString(),
                    idNumber = editText_employeeId.text.toString().toLong(),
                    address = editText_employeeLocation.text.toString(),
                    department = department
                )

                employee.department?.employee = listOf()

                GlobalScope.launch {
                    TestNaukaApi.invoke().postEmployee(employee)
                }
                backEmployeeActivity()
            } else Toast.makeText(this, "Заполните все данные", Toast.LENGTH_SHORT).show()
            true

        }

        R.id.del_button -> {
            GlobalScope.launch {
                TestNaukaApi.invoke().deleteEmployee(employeeIntent?.id!!)
            }
            Toast.makeText(this, "Сотрудник: ${employeeIntent?.name} удален", Toast.LENGTH_SHORT)
                .show()
            backEmployeeActivity()
            true
        }

        R.id.save_button -> {
            saveEmployee()
            backEmployeeActivity()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (employeeIntent == null) {
            menuInflater.inflate(R.menu.menu_save_new, menu)
        } else {
            menuInflater.inflate(R.menu.menu_save_del, menu)
        }
        return true
    }

    private fun getDepartments() {
        runBlocking {
            departmentList = TestNaukaApi.invoke().getAllDepartments().toMutableList()
        }
        departmentList.add(0, Department(null))
    }

    private fun fillFields() {
        editText_employeeName.setText(employeeIntent?.name, TextView.BufferType.EDITABLE)
        editText_employeeId.setText(
            employeeIntent?.idNumber.toString(),
            TextView.BufferType.EDITABLE
        )
        editText_employeeLocation.setText(employeeIntent?.address, TextView.BufferType.EDITABLE)
        val spinnerPosition =
            departmentList.indexOfFirst { it.id == employeeIntent?.department?.id ?: 0 }
        spinnerEmployee.setSelection(spinnerPosition)
    }

    private fun backEmployeeActivity() {
        val intent = Intent(this, EmployeeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveEmployee() {
        if (editText_employeeName.text.isNotBlank() &&
            editText_employeeId.text.isNotBlank() &&
            editText_employeeLocation.text.isNotBlank() &&
            department.id != null
        ) {

            employeeIntent?.name = editText_employeeName.text.toString()
            employeeIntent?.idNumber = editText_employeeId.text.toString().toLong()
            employeeIntent?.address = editText_employeeLocation.text.toString()
            employeeIntent?.department = department

            employeeIntent?.department?.employee = listOf()

            GlobalScope.launch {
                TestNaukaApi.invoke().putEmployee(employeeIntent!!)
            }
            backEmployeeActivity()
        } else Toast.makeText(this, "Заполните все данные", Toast.LENGTH_SHORT).show()
    }
}
