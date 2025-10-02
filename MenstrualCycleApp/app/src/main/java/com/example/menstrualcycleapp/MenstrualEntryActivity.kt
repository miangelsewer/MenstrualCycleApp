package com.example.menstrualcycleapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MenstrualEntryActivity : AppCompatActivity() {

    private lateinit var editStartDate: EditText
    private lateinit var editCycleLength: EditText
    private lateinit var spinnerFlowLevel: Spinner
    private lateinit var editSymptoms: EditText
    private lateinit var editNotes: EditText
    private lateinit var btnSubmit: Button

    private lateinit var dao: CycleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menstrual_entry)

        editStartDate = findViewById(R.id.editStartDate)
        editCycleLength = findViewById(R.id.editCycleLength)
        spinnerFlowLevel = findViewById(R.id.spinnerFlowLevel)
        editSymptoms = findViewById(R.id.editSymptoms)
        editNotes = findViewById(R.id.editNotes)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Populate spinner
        val flowLevels = arrayOf("Light", "Medium", "Heavy")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, flowLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFlowLevel.adapter = adapter

        dao = AppDatabase.getDatabase(this).cycleDao()

        btnSubmit.setOnClickListener {
            val entry = CycleEntity(
                startDate = editStartDate.text.toString(),
                cycleLength = editCycleLength.text.toString().toIntOrNull() ?: 0,
                flowLevel = spinnerFlowLevel.selectedItem.toString(),
                symptoms = editSymptoms.text.toString(),
                notes = editNotes.text.toString()
            )
            lifecycleScope.launch {
                dao.insert(entry)
            }
            finish() // return to main screen
        }
    }
}
