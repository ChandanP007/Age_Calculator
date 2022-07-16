package com.cnpdev.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeinMins : TextView? = null
    private var tvAgeinYrs : TextView? = null
    private var tvAgeinHrs : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnIdPicker)
        tvSelectedDate = findViewById(R.id.viewSelectedDate)
        tvAgeinMins = findViewById((R.id.inMins))
        tvAgeinYrs = findViewById(R.id.inYrs)
        tvAgeinHrs = findViewById(R.id.inHrs)

        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker()
    {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {_, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time/60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    val currentDateInMinutes = currentDate.time/60000
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    tvAgeinMins?.text = differenceInMinutes.toString()

                    val selectedDateInYrs = theDate.year
                    val currentDateInYears = currentDate.year
                    val differenceInYears = currentDateInYears - selectedDateInYrs
                    tvAgeinYrs?.text = differenceInYears.toString()

                    val selectedDateInHrs = selectedDateInMinutes/60
                    val currentDateInHours = currentDateInMinutes/60
                    val differenceInHours = currentDateInHours - selectedDateInHrs
                    tvAgeinHrs?.text = differenceInHours.toString()
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}