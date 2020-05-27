package ru.serg.testnauka.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.adapter.EmployeeAdapter
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Employee

class EmployeeActivity : AppCompatActivity() {
    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var recyclerView: RecyclerView
    private var employeeList: List<Employee>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        runBlocking {
            initConnection()
        }

        recyclerView = findViewById(R.id.recyclerView_employee)
        recyclerView.layoutManager = LinearLayoutManager(this)
        employeeList?.forEach {
            adapter.add(EmployeeAdapter(it))
        }

        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            val dep = item as EmployeeAdapter
            val intent = Intent(view.context, CreateEmployeeActivity::class.java)
            startActivity(intent.putExtra("employee", dep.employee))
        }
    }

    override fun onRestart() {
        initConnection()
        super.onRestart()
    }

    override fun onResume() {
        initConnection()
        super.onResume()
    }

    private fun initConnection() {
        runBlocking {
            employeeList = TestNaukaApi.invoke().getAllEmployees()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_button -> {
            val intent = Intent(this, CreateEmployeeActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }
}
