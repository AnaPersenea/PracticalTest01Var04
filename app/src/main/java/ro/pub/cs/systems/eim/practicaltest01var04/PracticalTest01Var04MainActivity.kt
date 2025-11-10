package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var04MainActivity : AppCompatActivity() {

    private lateinit var edit1: EditText
    private lateinit var edit2: EditText

    private lateinit var view: TextView

    private var stringToDisplay = ""

    private var edit1_string = ""
    private var edit2_string = ""


    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val action = intent.action
                val name = intent.getStringExtra("nume_thread")
                val group = intent.getStringExtra("grupa_thread")

                Toast.makeText(context, "Broadcast received! Action=$action\n Nume=$name, Group=$group", Toast.LENGTH_SHORT).show()
                Log.d("[MainActivity]", "onReceive called")
                Log.d("[Receiver]", "Action=$action\n Nume=$name, Group=$group")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction("ro.pub.cs.systems.eim.practicaltest01var04.ACTION_1")
            addAction("ro.pub.cs.systems.eim.practicaltest01var04.ACTION_2")
            addAction("ro.pub.cs.systems.eim.practicaltest01var04.ACTION_3")
        }
        registerReceiver(messageBroadcastReceiver, filter, Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var04_main)

        edit1 = findViewById(R.id.editText1)
        edit2 = findViewById(R.id.editText2)

        view = findViewById(R.id.textView)

        val displayButton = findViewById<Button>(R.id.displayButton)

        val check1 = findViewById<CheckBox>(R.id.checkBox1)
        val check2 = findViewById<CheckBox>(R.id.checkBox2)

        displayButton.setOnClickListener {
            edit1_string = edit1.toString()
            edit2_string = edit2.toString()

            if(check1.isChecked && edit1_string.isEmpty()){
                Toast.makeText(applicationContext,"Box checked and string empty", Toast.LENGTH_SHORT).show()
            }
            if (check2.isChecked && edit2_string.isEmpty()){
                Toast.makeText(applicationContext,"Box checked and string empty", Toast.LENGTH_SHORT).show()
            }

            if (check1.isChecked && !edit1_string.isEmpty() &&
                check2.isChecked && !edit2_string.isEmpty()){
                    stringToDisplay = "$edit1 $edit2"
                    view.text = stringToDisplay
            }


            stringToDisplay = "$edit1 $edit2"
            view.text = stringToDisplay

            // Start service
            if (!edit1_string.isEmpty() && !edit2_string.isEmpty()) {
                val serviceIntent = Intent(this, PracticalTest01Var04Service::class.java)
                serviceIntent.putExtra("name_main", edit1_string)
                serviceIntent.putExtra("group_main", edit2_string)
                startService(serviceIntent)
                Log.d("[MainActivity]", "Both fields completed – Service started")
            }
        }

        val activityResultsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultMsg = result.data?.getStringExtra("result")
                    Toast.makeText(this, "Result: $resultMsg", Toast.LENGTH_LONG).show()
                }
            }

        val nextButton = findViewById<Button>(R.id.nextButton)

        nextButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01Var04SecondaryActivity::class.java)

            intent.putExtra("val1", edit1.text.toString())
            intent.putExtra("val2", edit2.text.toString())

            activityResultsLauncher.launch(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("Verificare", "onSaveInstanceState called")
        super.onSaveInstanceState(outState)

        // Salvăm valorile contorilor
        outState.putString("name", edit1_string)
        outState.putString("group", edit2_string)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("Verificare", "onRestoreInstanceState called")
        super.onRestoreInstanceState(savedInstanceState)

        edit1_string =savedInstanceState.getString("name", "")
        edit2_string =savedInstanceState.getString("group", "")

        edit1.setText(edit1_string)
        edit2.setText(edit2_string)
    }
}