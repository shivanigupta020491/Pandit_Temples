package com.indtrack.pandit_temples

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var mAuth: FirebaseAuth? = null
    var userName:EditText ? = null
    var password:EditText ? = null
    var loginBtn:Button ? = null
    var signUpBtn: TextView? = null
    var forgotPassword: TextView? = null
    private var progressDialog: ProgressBar? = null
    private var builder: AlertDialog.Builder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initializeView()
        setListeners()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
       private fun getAuthConnentionToFirebase() {
           // Initialize Firebase Auth
           progressDialog!!.visibility = View.VISIBLE
           mAuth = FirebaseAuth.getInstance();
//
           if (mAuth!!.currentUser != null) {
               startActivity(Intent(this@LoginActivity, MainActivity::class.java))
               finish()
               Toast.makeText(this, "user is already available ", Toast.LENGTH_SHORT).show()
           }

               var email = userName!!.text.toString()
               var pass = password!!.text.toString()

               mAuth!!.createUserWithEmailAndPassword(email, pass)
                   .addOnCompleteListener { task ->
                       progressDialog!!.visibility = View.GONE
                       if (task.isSuccessful) {
                           Toast.makeText(this, "user email create ", Toast.LENGTH_SHORT).show()
                           startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                           finish()
                           val user = mAuth!!.currentUser
                         //  updateUI(user)
                       } else {
                           Toast.makeText(this, "user email not create ", Toast.LENGTH_SHORT).show()
                          // updateUI(null);
                       }
                   }

           //authenticate user
           //authenticate user
           mAuth!!.signInWithEmailAndPassword(email, pass)
               .addOnCompleteListener(this@LoginActivity,
                   OnCompleteListener<AuthResult?> { task ->
                       // If sign in fails, display a message to the user. If sign in succeeds
                       // the auth state listener will be notified and logic to handle the
                       // signed in user can be handled in the listener.
                       progressDialog!!.setVisibility(View.GONE)
                       if (!task.isSuccessful) {
                           // there was an error
                           if (password!!.length() < 6) {
                               password!!.setError(getString(R.string.minimum_password))
                           } else {
                               Toast.makeText(this@LoginActivity, getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                           }
                       } else {
                           val intent = Intent(this@LoginActivity, MainActivity::class.java)
                           startActivity(intent)
                           finish()
                       }
                   })



       }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth!!.currentUser
        //updateUI(currentUser)
    }

        private fun initView() {
            userName = findViewById(R.id.userNameEditText)
            password = findViewById(R.id.passwordEditText)
            loginBtn = findViewById(R.id.loginButton)
            forgotPassword = findViewById(R.id.forgotPasswordText)
            signUpBtn = findViewById(R.id.signUpBtn)
            progressDialog = findViewById(R.id.progressBar)
        }

        private fun initializeView(){
//            mAuth = FirebaseAuth.getInstance();
            progressDialog = ProgressBar(this@LoginActivity)
            //progressDialog.
            //progressDialog.m("Loading...")
            builder = AlertDialog.Builder(this@LoginActivity)
        }

        private fun setListeners() {
            loginBtn!!.setOnClickListener(this)
            signUpBtn!!.setOnClickListener(this)
            forgotPassword!!.setOnClickListener(this)
        }


        fun checkInternetConnection(): Boolean {
            var connected = false
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connected = if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .state == NetworkInfo.State.CONNECTED
            ) {
                //we are connected to a network
                true
            } else {
                false
            }
            return connected
        }

         fun isValid(): Boolean {
            return if (TextUtils.isEmpty(userName!!.getText())) {
                userName!!.setError("Enter userName")
                false
            } else if (TextUtils.isEmpty(password!!.getText())) {
                password!!.setError("Enter Password")
                false
            } else if (password!!.getText().length < 8) {
                password!!.setError("Password is too short")
                false
            } else {
                true
            }
        }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton ->{
                System.out.println(">>>>>>>> login btn")
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)

            }

//                if (isValid()) {
//                if (checkInternetConnection()) {
//                    getAuthConnentionToFirebase()
//                }
            //}
            R.id.signUpBtn -> {
                val intent1 = Intent(this, SignupActivity::class.java)
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