package com.indtrack.pandit_temples

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.indtrack.pandit_temples.pojo.TemplePojo
import com.indtrack.pandit_temples.pojo.mythologicalPojo
import com.indtrack.pandit_temples.ui.mythological.MythologicalViewModel
import java.text.SimpleDateFormat
import java.util.*

class MythologicalActivity : AppCompatActivity(), View.OnClickListener {

    var mythologicalViewModel: MythologicalViewModel? = null
    var noteTitle: EditText? = null
    var noteDetail: EditText? = null
    var save: Button? = null
    var edit: Button? = null
    private lateinit var date: TextView
    private lateinit var dateLayout: LinearLayout
    var toolbar: Toolbar? = null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private lateinit var progressBar: ProgressBar
    private var builder: AlertDialog.Builder? = null
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mythological)

        // mythologicalViewModel = ViewModelProviders.of(this).get(MythologicalViewModel::class.java)
        initView()
        initInstance()
        setListeneres()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initView() {
        noteTitle = findViewById(R.id.noteTitleText1)
        noteDetail = findViewById(R.id.noteDetailText1)
        save = findViewById(R.id.saveBtn)
        edit = findViewById(R.id.editBtn)
        date = findViewById(R.id.dateText)
        dateLayout = findViewById(R.id.setDateBtn)
        toolbar = findViewById(R.id.toolbarM)

    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.reference
        setProgressBar()

        var dateTimeStamp1 = ServerValue.TIMESTAMP
        var dateTimeStamp2 = System.currentTimeMillis()

        System.out.println(">>>>>>>  time stamp " + dateTimeStamp1 + dateTimeStamp2)
//
//       //for system navigation bar color #E58221
//     getWindow().setNavigationBarColor(Color.parseColor("#ff8256"));

        builder = AlertDialog.Builder(this)

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        //for system navigation bar color #E58221
        // getWindow().setNavigationBarColor(resources.getColor(R.color.colorPrimaryDark)) //ae153b
    }
    private fun setProgressBar(){
        val layout = RelativeLayout(this)
        progressBar = ProgressBar(this,null,android.R.attr.progressBarStyleSmall)
        progressBar.setIndeterminate(true)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
    }

    private fun setListeneres() {

        save!!.setOnClickListener(this)
        edit!!.setOnClickListener(this)
        dateLayout.setOnClickListener(this)

    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        var id = v!!.id

        when (id) {
            R.id.saveBtn -> {
                if (isValid()){
                    uploadDataInFirebase()
                }

            }

            R.id.setDateBtn->{
                getDateByDatePicker()
            }
        }
    }

    private fun isValid(): Boolean {
        if (TextUtils.isEmpty(date!!.getText())) {
            date.setError("Select Date ")
            return false
        }else if (TextUtils.isEmpty(noteTitle!!.getText())) {
            noteTitle!!.setError("Enter title ")
            return false
        } else if (TextUtils.isEmpty(noteDetail!!.getText())) {
            noteDetail!!.setError("Enter Detail. ")
            return false

        } else {
            return true
        }
    }

    private fun uploadDataInFirebase() {
        progressBar.visibility = View.VISIBLE

        mDatabaseReference = mdatabase!!.reference.child("Mythological")

        var dateTimeStamp1 = ServerValue.TIMESTAMP
        var dateTimeStamp2 = System.currentTimeMillis()
        mDatabaseReference!!.push().setValue(
            mythologicalPojo(dateTimeStamp1,noteTitle!!.text.toString(),noteDetail!!.text.toString()))

        finish()


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

}