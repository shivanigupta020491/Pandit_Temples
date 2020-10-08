package com.indtrack.pandit_temples

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener {

    private var bottomNavigationView: BottomNavigationView? = null
    private var navigationView: NavigationView? = null
    private var navController: NavController? = null
    private var drawer: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var toolbar: Toolbar? = null
    private var profileName: TextView? = null
    private var profileEmail: TextView? = null
    private var profileImage: ImageView? = null
    private var snackbarText: TextView? = null
    private val iText: String? = null
    private var fragmentLayout: LinearLayout? = null
    private val galleryLayout: LinearLayout? = null
    private var headerView: View? = null
    private val dialog: AlertDialog? = null
    private var progressBar: ProgressBar? = null
    private val FCM_DEVICE_TOKEN: String? = null
    private var builder: AlertDialog.Builder? = null
    private val mAppBarConfiguration: AppBarConfiguration? = null
    private var tipLayout: LinearLayout? = null
    private var tipImage: ImageView? = null
    private val tipText: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
       initInstance()
        setListeners()
//        setFcmTokenFromServer()

//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_add, R.id.navigation_view))
////        setupActionBarWithNavController(navController, appBarConfiguration)
//       //navView.setupWithNavController(navController)
    }

    private fun setListeners() {
//        switchCompat.setOnClickListener(this);
        navigationView!!.setNavigationItemSelectedListener(this)

        //switchButtonFunction();
    }

    private fun initView() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar_main)
        drawer = findViewById(R.id.drawer_layout)
        fragmentLayout = findViewById(R.id.fragmentLayout)
        navigationView = findViewById(R.id.main_sidebar)
        snackbarText = findViewById(R.id.snackbarText)
        headerView = navigationView!!.getHeaderView(0)
        profileImage = headerView!!.findViewById(R.id.nav_header_imageView)
        profileName = headerView!!.findViewById(R.id.nav_header_textView)
        profileEmail = headerView!!.findViewById(R.id.nav_header_emailView)

        NavigationUI.setupWithNavController(bottomNavigationView!!, navController!!)

        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle!!)
        toggle!!.syncState()
        builder = AlertDialog.Builder(this@MainActivity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)

        /*
        * set navigation drawer with bottom navigation
        * */

       // bottomNavigationView.setupWithNavController(bottomNavigationView,navController)
        bottomNavigationView!!.setupWithNavController(navController!!)

        // for set title in fragments
        navController!!.addOnDestinationChangedListener(object : OnDestinationChangedListener {
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

                when (destination.id) {
                    R.id.navigation_add -> {
                        run { toolbar!!.title = "Pandit" }

                    }
                    R.id.navigation_home -> {
                        run { toolbar!!.title = "Home" }
                        //run { toolbar!!.title = "Pendding" }
                    }
                    R.id.navigation_view -> {
                        toolbar!!.title = "Temple"
                    }

                    R.id.navigation_mythological -> {
                        run { toolbar!!.title = "Mythological" }
                        //run { toolbar!!.title = "Pendding" }
                    }
                    R.id.navigation_templeDetail -> {
                        toolbar!!.title = "Temple Detail"
                    }
                }
            }
        })

        //for system navigation bar color #E58221
        window.navigationBarColor = resources.getColor(R.color.colorPrimaryDark)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        println(">>>>>>>>> navigation item id $id")
        when(id){

            R.id.nav_temple ->{
                println(">>>>>>>>> navigation item id 1 $id")
                var intent = Intent(this,TempleActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_mythological->{
                var intent = Intent(this,MythologicalActivity::class.java)
                startActivity(intent)

            }

            R.id.nav_templeDetail->{
                var intent = Intent(this,TempleActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_pandit->{
                var intent = Intent(this,PanditActivity::class.java)
                startActivity(intent)

            }
        }

        return true
    }
    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }


}