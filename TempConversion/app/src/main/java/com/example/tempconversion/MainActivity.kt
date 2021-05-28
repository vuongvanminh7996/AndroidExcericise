package com.example.tempconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.w3c.dom.Text
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tempRadioGroup:RadioGroup = findViewById(R.id.Temp)
        var button:Button = findViewById(R.id.ConvertButton)
        val editText:EditText = findViewById(R.id.Input)

        // Actions to be performed
        // when Submit button is clicked
        button.setOnClickListener {

            // Getting the checked radio button id
            // from the radio group
            val selectedOption: Int = tempRadioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            var radioButton:RadioButton = findViewById(selectedOption)
            var resultTest:TextView = findViewById(R.id.Result)
            var result:String = ""
            var history:TextView = findViewById(R.id.History)

            if(editText.text.isNotEmpty())
            {
            if(radioButton.text == "Fahrenheit-to-Celsius")
            {
                result = (((editText.text.toString().toDouble() -32.0) *5.0 )/ 9.0).toString()
                history.setText("F to C: ${editText.text} -> ${result} \n" + history.text.toString())
//                    history.text.toString().addCharAtIndex("C to F: ${editText.text.toString()} \uF0E8 ${result} \n",0)
            }
            else if(radioButton.text == "Celsius-to-Fahrenheit")
            {
                result = ((editText.text.toString().toDouble() * 9.0 / 5.0) + 32.0).toString()
                history.setText("C to F: ${editText.text.toString()} -> ${result} \n" + history.text.toString())
            }
            resultTest.setText(result)
            }



            // Displaying text of the checked radio button in the form of toast
//            Toast.makeText(baseContext, result, Toast.LENGTH_SHORT).show()
        }
    }

    fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()
}
