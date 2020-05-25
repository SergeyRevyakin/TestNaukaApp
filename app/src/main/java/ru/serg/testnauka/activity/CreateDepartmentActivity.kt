package ru.serg.testnauka.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_create_department.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serg.testnauka.R
import ru.serg.testnauka.api.TestNaukaApi
import ru.serg.testnauka.model.Department

class CreateDepartmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_department)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.save_new_button -> {
            if (editText_departmentLocation.text.isNotBlank() &&
                editText_departmentName.text.isNotBlank()
            ) {
                val department = Department(
                    location = editText_departmentLocation.text.toString(),
                    name = editText_departmentName.text.toString()
                )

                GlobalScope.launch {
                    TestNaukaApi.invoke().postDepartment(department)
                }
                val intent = Intent(this, DepartmentActivity::class.java)
                startActivity(intent)
                finish()
            }
            else Toast.makeText(this,"Заполните все данные", Toast.LENGTH_SHORT).show()
            true

        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save_new, menu)
        return true
    }

}
