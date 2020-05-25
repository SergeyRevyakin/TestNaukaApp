package ru.serg.testnauka.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_department_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi


import ru.serg.testnauka.model.Department

class DepartmentDetailsActivity : AppCompatActivity() {

    private lateinit var department: Department

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            backDepartment()
        }

        department = intent.getSerializableExtra("department") as Department

        textView_departmentID.text = department.id.toString()
        editText_departmentName.setText(department.name, TextView.BufferType.EDITABLE)
        editText_departmentLocation.setText(department.location, TextView.BufferType.EDITABLE)
        textView_numberOfEmpl.text = department.employee?.size.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.del_button -> {
            GlobalScope.launch {
                TestNaukaApi.invoke().deleteDepartment(department.id!!)
            }
            Toast.makeText(this, "Отдел: ${department.name} удален", Toast.LENGTH_SHORT).show()
            backDepartment()
            true
        }

        R.id.save_button -> {
            department.location = editText_departmentLocation.text.toString()
            department.name = editText_departmentName.text.toString()
            department.employee = listOf()
            GlobalScope.launch {
                TestNaukaApi.invoke().putDepartment(department)
            }
            Toast.makeText(
                this,
                "Изменения в отделе: ${department.name} сохранены",
                Toast.LENGTH_SHORT
            ).show()
            backDepartment()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save_del, menu)
        return true
    }

    private fun backDepartment() {
        val intent = Intent(this, DepartmentActivity::class.java)
        startActivity(intent)
        finish()
    }
}
