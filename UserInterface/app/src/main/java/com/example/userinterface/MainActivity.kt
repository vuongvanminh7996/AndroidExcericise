package com.example.userinterface

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
//    lateinit var radioButtonSize: RadioButton
//    lateinit var radioButtonTortilla: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val smsManager = SmsManager.getDefault()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),1);
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),1);


        var sizeRadioGroup: RadioGroup? = findViewById(R.id.Size)
        var tortillaRadioGroup: RadioGroup? = findViewById(R.id.Tortilla)
        var chipGroupFilling: ChipGroup = findViewById(R.id.Filling)
        var chipGroupBeverage: ChipGroup = findViewById(R.id.Beverage)
        var buttonWebView:Button = findViewById(R.id.ButtonWebView)
        var webView: WebView = findViewById(R.id.webView)

        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl("https://www.merriam-webster.com/dictionary/taco/")

        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
        webView.isInvisible = true;

        var buttonLocation:Button = findViewById(R.id.Location)

        buttonLocation.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.google.com/maps")
            startActivity(openURL)
        }




//        chipGroupFilling.setOnCheckedChangeListener{group,checkedId:Int ->
//            // Get the checked chip instance from chip group
//
//            val chip:Chip = findViewById(checkedId)
//
//            chip?.let {
//                // Show the checked chip text on toast message
//                toast("${it.text} checked")
//            }
//        }
        buttonWebView.setOnClickListener {
            // WebViewClient allows you to handle
            // onPageFinished and override Url loading.
            webView.isInvisible = false
        }

        button = findViewById(R.id.submitButton);


        // Actions to be performed
        // when Submit button is clicked
        button.setOnClickListener {

            // Getting the checked radio button id
            // from the radio group
            val selectedOptionSize: Int = sizeRadioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            val radioButtonSize: RadioButton = findViewById(selectedOptionSize)

            val selectedOptionTortilla: Int = tortillaRadioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            val radioButtonTortilla: RadioButton = findViewById(selectedOptionTortilla)

            val fillings:String = chipGroupFilling?.children
                ?.toList()
                ?.filter { (it as Chip).isChecked }
                ?.joinToString(", ") { (it as Chip).text }

            val beverages:String = chipGroupBeverage?.children
                ?.toList()
                ?.filter { (it as Chip).isChecked }
                ?.joinToString(", ") { (it as Chip).text }

//            val phoneNumber = "5554"
//            val message = "Size: " + radioButtonSize.text +
//                    "\n Tortilla type: " + radioButtonTortilla.text + "\n Filling: " + fillings + "\n Beverage: " + beverages
//            val intent = Intent(
//                Intent.ACTION_SENDTO,
//                Uri.parse("sms:$phoneNumber")
//            )
//            intent.putExtra("sms_body", message)
//            startActivity(intent)
//
            smsManager.sendTextMessage(
                "5554", null,
                "Size: " + radioButtonSize.text +
                    "\n Tortilla type: " + radioButtonTortilla.text + "\n Filling: " + fillings + "\n Beverage: " + beverages,
                null, null
            )


        }


    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            webView.isInvisible = true;
    }

    // Toast extension method
    fun Context.toast(message:String)=
        Toast.makeText(baseContext,message,Toast.LENGTH_SHORT).show()






}
