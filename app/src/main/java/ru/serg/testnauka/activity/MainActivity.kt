package ru.serg.testnauka.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.DependencyComponent
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Department
import ru.serg.testnauka.model.Employee
import javax.inject.Inject

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

        val employeeList = getEmlp()
    }

    fun getEmlp(): List<Employee> {
        var result: List<Employee> = listOf()
        GlobalScope.launch {
            result = TestNaukaApi.invoke().getAllEmployees()
        }
        return result
    }
}

