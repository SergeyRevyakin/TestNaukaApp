package ru.serg.testnauka.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_calendar.*
import ru.serg.testnauka.R

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            Toast.makeText(this,"Its ${dayOfMonth} of ${month}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DayOfCalendarActivity::class.java)
            startActivity(intent.putExtra("date", "${year}-0${month+1}-${dayOfMonth}"))
        }
    }

}
