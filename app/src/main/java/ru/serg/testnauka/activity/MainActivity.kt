package ru.serg.testnauka.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Department
import ru.serg.testnauka.model.Employee

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        buttonDepartment.setOnClickListener {
            val intent = Intent(this, DepartmentActivity::class.java)
            startActivity(intent)
        }

        buttonEmployee.setOnClickListener {
            val intent = Intent(this, EmployeeActivity::class.java)
            startActivity(intent)
        }

        buttonCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        loadRandomFact()
        // }
    }

    private fun loadRandomFact() {

        GlobalScope.launch {
            test2()
        }

    }

    private suspend fun test2() {
        val departResp = TestNaukaApi.invoke().getAllDepartments()

        val emplResp = TestNaukaApi.invoke().getAllEmployees()

        //val singleEmlp = TestNaukaApi.invoke().getEmployeeById(1)


        val empList: List<Employee> = listOf(
            Employee(name = "LOL", idNumber = 1111L, address = "KEK"),
            Employee(name = "BERG", idNumber = 1111L, address = "KFAd"),
            Employee(name = "WERO", idNumber = 1111L, address = "FSD")
        )
        val dep = Department(name = "DOES", location = "IT WORKS", employee = empList)

        val strrr = "!!!"

        TestNaukaApi.invoke().postDepartment(dep)

        runOnUiThread {
            textview.text = departResp?.get(1)?.name ?: "LOL"
            //textView2.text = singleEmlp?.name
            textView.text = emplResp?.first()?.department?.name ?: "LOL"
            textview.setTextColor(getColor(R.color.secondaryDarkColor))
        }
    }
}

