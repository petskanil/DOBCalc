package com.example.dobcalc_2

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dobcalc_2.ui.theme.DOBCalc_2Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvCalculatedMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.dateButton)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvCalculatedMinutes = findViewById(R.id.calculatedMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Do something with the input parameters of the lambda function.
            // This function is only run if the user presses the submit button.
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)
            val theDate = sdf.parse(selectedDate)
            theDate?.let{// Only run if theDate is not null
                val selectedDateInMinutes = theDate.time/60000 // .time gives the time since 01.01.1970 in milliseconds

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                currentDate?.let {// Only run if currentDate is not null
                    val currentDateInMinutes = currentDate.time/60000

                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                    tvCalculatedMinutes?.text = differenceInMinutes.toString()
                }
            }

            Toast.makeText(this,"DatePicker Works!", Toast.LENGTH_SHORT).show()
        },year,month,day)
    dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // - 24 hours in ms
    dpd.show()// Have to use .show() to actually show the dialog
    }
}