package ro.pub.cs.systems.eim.practicaltest01var04

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var04SecondaryActivity  : AppCompatActivity() {
    private lateinit var firstText: TextView
    private lateinit var secondText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Verificare", "SECONDARY onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var04_secondary)

        val receivedVal1 = intent.getIntExtra("val1", 0)
        val receivedVal2 = intent.getIntExtra("val2",0)

        firstText = findViewById(R.id.firstTextView)
        secondText = findViewById(R.id.secondTextView)

        firstText.text = receivedVal1.toString()
        secondText.text = receivedVal2.toString()

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("result", "OK")
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("result", "Cancel")
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}