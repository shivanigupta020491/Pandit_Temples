package com.indtrack.pandit_temples

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private var signupButton: Button? = null
    private var loginButton: TextView? = null
    private var signInLayout: LinearLayout? = null
    private var userNameLayout: LinearLayout? = null
    private var typeSpinner: Spinner? = null
    private var userNameEditText: EditText? = null
    private var addressEditRext: EditText? = null
    private var contactEditText: EditText? = null
    private var cityEditText: EditText? = null
    private var stateEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var confirmPasswordEditText: EditText? = null
    private val alertDialog: AlertDialog? = null
    private val otpText: EditText? = null
    private var builder: AlertDialog.Builder? = null
    private val otpNumber: String? = null
    private val spinnerPopupValue: String? = null
    private val spinnerPopupValueId: String? = null
    private val spinnerPopupPosition: Int? = null
    private var progressDialog: ProgressDialog? = null
    private val loginId: String? = null
   // private val sessionManager: SessionManager? = null
    private val registerBtn: TextView? = null
//    var spinnerList: ArrayList<String?> = ArrayList<Any?>()
//    var spinnerListId: ArrayList<String?> = ArrayList<Any?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView()
        initInstance()
        setListeners()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }



    private fun initView() {
        signupButton = findViewById<Button>(R.id.userRegisterBtn)
        userNameEditText = findViewById<EditText>(R.id.userNameET)
        contactEditText = findViewById<EditText>(R.id.userContactET)
        addressEditRext = findViewById<EditText>(R.id.userAddressET)
        emailEditText = findViewById<EditText>(R.id.userEmailET)
        cityEditText = findViewById<EditText>(R.id.userCityET)
//        stateEditText = findViewById<EditText>(R.id.userStateET)
        passwordEditText = findViewById<EditText>(R.id.passwordSignup)
        confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordSignup)
        loginButton = findViewById<EditText>(R.id.loginBtn)

//        cityEditText = findViewById(R.id.citySignup);
    }

    private fun initInstance() {
        progressDialog = ProgressDialog(this@SignupActivity)
        progressDialog!!.setMessage("Loading...")
        builder = AlertDialog.Builder(this@SignupActivity)

    }

    private fun setListeners() {
        loginBtn!!.setOnClickListener(this)
        signupButton!!.setOnClickListener(this)
       // forgotPassword!!.setOnClickListener(this)
    }

    private fun isValid(): Boolean {
        return if (TextUtils.isEmpty(userNameEditText!!.text)) {
            userNameEditText!!.error = "Enter Use Name."
            userNameEditText!!.isFocusable = true
            false
        } else if (TextUtils.isEmpty(emailEditText!!.text)) {
            emailEditText!!.error = "Enter Email Address."
            emailEditText!!.isFocusable = true
            false
        }else if (TextUtils.isEmpty(contactEditText!!.text)) {
            contactEditText!!.error = "Enter Contact No."
            contactEditText!!.isFocusable = true
            false
        } else if (contactEditText!!.text.length < 10 ||
            contactEditText!!.text.length > 10
        ) {
            contactEditText!!.error = "Enter 10 digit Contact No."
            contactEditText!!.isFocusable = true
            false
        } else if (TextUtils.isEmpty(addressEditRext!!.text)) {
            addressEditRext!!.error = "Enter Address."
            addressEditRext!!.isFocusable = true
            false
            //
//        } else if (!ReUsuable_Function.isValidEmail(
//                emailEditText!!.text.toString(),
//                emailEditText)) {
//            false
        } else if (TextUtils.isEmpty(cityEditText!!.text)) {
            cityEditText!!.error = "Enter City"
            cityEditText!!.isFocusable = true
            false
        }else if (TextUtils.isEmpty(stateEditText!!.text)) {
            stateEditText!!.error = "Enter State"
            stateEditText!!.isFocusable = true
                false

        } else if (passwordEditText!!.text.length < 8 ||
            passwordEditText!!.text.length > 16
        ) {
            passwordEditText!!.error = "Enter minimum 8 digit password"
            passwordEditText!!.isFocusable = true
            false
//        } else if (!ReUsuable_Function.isPasswordValidation(passwordEditText)) {
//            false
        } else if (TextUtils.isEmpty(confirmPasswordEditText!!.text)) {
            confirmPasswordEditText!!.error = "Enter Password"
            confirmPasswordEditText!!.isFocusable = true
            false
        } else if (confirmPasswordEditText!!.text.toString() != passwordEditText!!.text
                .toString()
        ) {
            confirmPasswordEditText!!.error = "Password does not match"
            confirmPasswordEditText!!.isFocusable = true
            false
        } else {
            true
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.loginBtn ->{
                val intent1 = Intent(this, LoginActivity::class.java)
                startActivity(intent1)
            }

//                if (isValid()) {
//                if (checkInternetConnection()) {
//                    getAuthConnentionToFirebase()
//                }
            //}
            R.id.userRegisterBtn -> {
                val intent1 = Intent(this, LoginActivity::class.java)
                startActivity(intent1)

            }
            R.id.forgotPasswordText -> {
//                val intent1 = Intent(this, ForgotPasswordActivity::class.java)
//                startActivity(intent1)
//                finish()
            }
        }
    }
}