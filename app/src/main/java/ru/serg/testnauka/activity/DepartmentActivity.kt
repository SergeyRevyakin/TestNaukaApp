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
import ru.serg.testnauka.adapter.DepartmentAdapter
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Department

class DepartmentActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var recyclerView: RecyclerView
    private var departmentList: List<Department>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        initConnection()

        recyclerView = findViewById(R.id.recyclerView_department)
        recyclerView.layoutManager = LinearLayoutManager(this)
        departmentList?.forEach {
            adapter.add(DepartmentAdapter(it))
        }

        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            val dep = item as DepartmentAdapter
            val intent = Intent(view.context, DepartmentDetailsActivity::class.java)
            startActivity(intent.putExtra("department", dep.department))
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
            departmentList = TestNaukaApi.invoke().getAllDepartments()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_button -> {
            val intent = Intent(this, CreateDepartmentActivity::class.java)
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
