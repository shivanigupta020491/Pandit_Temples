package com.indtrack.pandit_temples

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.indtrack.pandit_temples.pojo.TemplePojo
import com.indtrack.pandit_temples.ui.temple.TempleViewModel
import java.text.SimpleDateFormat
import java.util.*

class TempleActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var templeViewModel: TempleViewModel
    private lateinit var name: EditText
    private lateinit var location: EditText
    private lateinit var activity: EditText
    private lateinit var date: TextView
    private lateinit var dateLayout: LinearLayout
    private lateinit var submitBtn: TextView
    private lateinit var progressBar: ProgressBar
    private var builder: AlertDialog.Builder? = null
    private var toolbar: Toolbar? = null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0
    private  var maxId:Int = 0
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temple)

        initView()
        initInstance()
        setListener()
    }

    private fun initView() {
        name = findViewById(R.id.templeName)
        location = findViewById(R.id.templeLocation)
        activity = findViewById(R.id.templeActivity)
        date = findViewById(R.id.dateText)
        dateLayout = findViewById(R.id.setDateBtn)
        submitBtn = findViewById(R.id.submitTempleBtn)
        toolbar = findViewById(R.id.toolbar)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar!!.setTitle("Temple ")

        val calendarStart = Calendar.getInstance()
        val sdfDate = SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdfDate.format(calendarStart.time)
        date.setText(currentDate)

        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.reference
        setProgressBar()
//
//       //for system navigation bar color #E58221
//     getWindow().setNavigationBarColor(Color.parseColor("#ff8256"));

        builder = AlertDialog.Builder(this)

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
      getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        //for system navigation bar color #E58221
        // getWindow().setNavigationBarColor(resources.getColor(R.color.colorPrimaryDark)) //ae153b
    }

    private fun setListener() {
        dateLayout.setOnClickListener(this)
        submitBtn.setOnClickListener(this)
    }

    private fun setProgressBar(){
        val layout = RelativeLayout(this)
        progressBar = ProgressBar(this,null,android.R.attr.progressBarStyleSmall)
        progressBar.setIndeterminate(true)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
    }

    override fun onClick(v: View?) {
        var id = v!!.id

        when(id){
            R.id.setDateBtn->{
                getDateByDatePicker()
            }

            R.id.submitTempleBtn->{

                if (isValid()){
                    uploadDataInFirebase()
                }

            }
        }
    }

    private fun getDateByDatePicker() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val sdf = SimpleDateFormat("dd MMMM yyyy")
        val datePickerDialog = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val calendarStart = Calendar.getInstance()
                calendarStart[Calendar.YEAR] = year
                calendarStart[Calendar.MONTH] = monthOfYear
                calendarStart[Calendar.DAY_OF_MONTH] = dayOfMonth
                val dateText = sdf.format(calendarStart.time)
                date.setText(dateText)

                //dateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                // for date
                //                        dateText = sdf.format(dateTextView.getText().toString());
                //dateTextView.setText(dateText);
            }, mYear, mMonth, mDay
        )

        // which sets today's date as minimum date and all the past dates are disabled.
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun isValid(): Boolean {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Enter Temple Name")
            return false
        } else if (TextUtils.isEmpty(location.getText())) {
            location.setError("Enter Temple Location ")
            return false
        } else if (TextUtils.isEmpty(activity.getText())) {
            activity.setError("Enter Activity Detail")
            return false
        } else if (TextUtils.isEmpty(date.getText())) {
            date.setError("Select Activity Date")
            return false

        } else {
            return true
        }
    }

    private fun uploadDataInFirebase() {
        progressBar.visibility = View.VISIBLE

        mDatabaseReference = mdatabase!!.reference.child("Temple")

        mDatabaseReference!!.push().setValue(
            TemplePojo(name.text.toString(),location.text.toString(),
            activity.text.toString(),date.text.toString()))

        finish()

    }



}