package com.indtrack.pandit_temples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.indtrack.pandit_temples.pojo.PanditPojo
import com.indtrack.pandit_temples.pojo.TemplePojo
import com.indtrack.pandit_temples.ui.temple.TempleViewModel
import java.text.SimpleDateFormat
import java.util.*

class PanditActivity : AppCompatActivity(),View.OnClickListener{

    private lateinit var templeViewModel: TempleViewModel
    private lateinit var name: EditText
    private lateinit var contactNo: EditText
    private lateinit var description: EditText
    private lateinit var work: EditText
    private lateinit var submitBtn: TextView
    private lateinit var progressBar: ProgressBar
    private var builder: AlertDialog.Builder? = null
    private var toolbar: Toolbar? = null
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pandit)

        initView()
        initInstance()
        setListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


    private fun initView() {
        name = findViewById(R.id.panditName)
        contactNo = findViewById(R.id.panditContact)
        description = findViewById(R.id.panditDescription)
        work = findViewById(R.id.panditWork)
        submitBtn = findViewById(R.id.submitPanditBtn)
        toolbar = findViewById(R.id.toolbar)

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
        toolbar!!.setTitle("Pandit ")

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

            R.id.submitPanditBtn->{

                if (isValid()){
                    uploadDataInFirebase()
                }

            }
        }
    }

    private fun isValid(): Boolean {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Enter Pandit Name")
            return false
        } else if (TextUtils.isEmpty(contactNo.getText())) {
            contactNo.setError("Enter Contact No. ")
            return false

        } else if (contactNo.length() > 10 || contactNo.length() < 10) {
            contactNo.setError("Enter 10 digit Mobile No. ")
            return false

        } else if (TextUtils.isEmpty(description.getText())) {
            description.setError("Enter Pandit Detail")
            return false

        } else if (TextUtils.isEmpty(work.getText())) {
            work.setError("Enter Work Detail")
            return false

        } else {
            return true
        }
    }

    private fun uploadDataInFirebase() {
        progressBar.visibility = View.VISIBLE

        mDatabaseReference = mdatabase!!.reference.child("Pandit")

        mDatabaseReference!!.push().setValue(
            PanditPojo(name.text.toString(),contactNo.text.toString(),
                description.text.toString(),work.text.toString())
        )

        finish()

    }



}