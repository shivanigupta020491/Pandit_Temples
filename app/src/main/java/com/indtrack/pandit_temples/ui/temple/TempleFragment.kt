package com.indtrack.pandit_temples.ui.temple

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.indtrack.pandit_temples.R
import com.indtrack.pandit_temples.TempleActivity
import com.indtrack.pandit_temples.adapter.TempleDataAdapter
import com.indtrack.pandit_temples.pojo.TemplePojo
import java.text.SimpleDateFormat
import java.util.*


class TempleFragment : Fragment(), View.OnClickListener {

    private var searchView: SearchView? = null
    private var recyclerView: RecyclerView? = null
    private var noDataShowLayout: LinearLayout? = null
    private var listShowLayout: LinearLayout? = null
    private lateinit var templeViewModel: TempleViewModel
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var name:EditText
    private lateinit var location:EditText
    private lateinit var activity:EditText
    private lateinit var date:TextView
    private lateinit var progressBar: ProgressBar
    private var builder: AlertDialog.Builder? = null
    private var mdatabase:FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var fabButton: FloatingActionButton? = null
    var templeList:ArrayList<TemplePojo> = ArrayList()
    var adapter: TempleDataAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        templeViewModel = ViewModelProvider(this).get(TempleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_temple, container, false)

        swipeRefreshLayout = root.findViewById(R.id.swipeAssign)
        searchView = root.findViewById(R.id.assignmentTeacherSearc)
        recyclerView = root.findViewById(R.id.recyclerListAssignment)
        noDataShowLayout = root.findViewById(R.id.dataLayoutAssign)
        listShowLayout = root.findViewById(R.id.listShowLayoutAssign)
        fabButton = root.findViewById(R.id.addAssignmentButton)

        //val textView: TextView = root.findViewById(R.id.text_notifications)
        templeViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })

        initInstance()
        setListener()
        getDataFromFirebase()
        return root

    }

    private fun initInstance() {

        val calendarStart = Calendar.getInstance()
        val sdfDate = SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdfDate.format(calendarStart.time)
        //date.setText(currentDate)

        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.reference
        setProgressBar()
//
//       //for system navigation bar color #E58221
//        getWindow().setNavigationBarColor(Color.parseColor("#ff8256"));

        builder = AlertDialog.Builder(this!!.requireActivity())

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
//
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        //for system navigation bar color #E58221
       // getWindow().setNavigationBarColor(resources.getColor(R.color.colorPrimaryDark)) //ae153b
    }

    private fun setListener() {


//        editTLayout.setOnClickListener(this);
        searchView!!.setOnClickListener(this)
        swipeRefreshLayout!!.setOnClickListener(this)
        fabButton!!.setOnClickListener(this)


    }

    private fun setProgressBar(){
        val layout = RelativeLayout(getActivity())
        progressBar = ProgressBar(getActivity(),null,android.R.attr.progressBarStyleSmall)
        progressBar.setIndeterminate(true)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
    }

    override fun onClick(v: View?) {
        var id = v!!.id

        when(id){

            R.id.addAssignmentButton-> {
                var intent =  Intent(getActivity(), TempleActivity::class.java);
                startActivity(intent);


            }
        }
    }



    private fun uploadDataInFirebase() {
       progressBar.visibility = View.VISIBLE

       mDatabaseReference = mdatabase!!.reference.child("Temple")
       mDatabaseReference!!.setValue(TemplePojo(name.text.toString(),location.text.toString(),
           activity.text.toString(),date.text.toString()))


    }

    private fun getDataFromFirebase(){
        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.reference

        val myTopPostsQuery: Query = mDatabaseReference!!.child("Temple")
        myTopPostsQuery!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    System.out.println(">>>>>>>  postSnapshot " + dataSnapshot)

                    var templeValue1:Map<String,String> = postSnapshot.value as Map<String,String>
                    System.out.println(">>>>>>>  postSnapshot key 2" + templeValue1.get("location"))

                    var templePojo = TemplePojo(templeValue1.get("name"),templeValue1.get("location"),
                        templeValue1.get("activity"),templeValue1.get("date"))

                    //  templeList.add(templePojo)


//                    postSnapshot.children.forEach {
//
//                        System.out.println(">>>>>>>>pandit detail list 1 " + it.value.)
//
//                    }
////

                    templeList.add(templePojo)
                }
                if(templeList.size > 0){
                    noDataShowLayout!!.visibility = View.GONE
                    listShowLayout!!.visibility = View.VISIBLE
                }

                addRecyclerview(templeList);
            }

        })



    }

    private fun addRecyclerview(templeList: ArrayList<TemplePojo>) {
        System.out.println("pandit detail list " + templeList[0].name)

        adapter = TempleDataAdapter(templeList,"TempleFragment")
        recyclerView!!.layoutManager = LinearLayoutManager(getActivity())
        recyclerView!!.adapter = adapter
        recyclerView!!.setHasFixedSize(false)
        adapter!!.notifyDataSetChanged()
    }

//    private fun viewFile() {
//        itemsList = ArrayList<String>()
//        urlList = ArrayList<String>()
//        mDatabaseRef =
//            FirebaseDatabase.getInstance().getReference(AssignmentFragment.DATABASE_PATH_UPLOADS)
//
//        //adding an event listener to fetch values
//        mDatabaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                //dismissing the progress dialog
//                //progressDialog.dismiss();
//
//                //iterating through all the values in database
//                for (postSnapshot in snapshot.children) {
//                    val url =
//                        postSnapshot.getValue(String::class.java)!! // return url
//                    val fileName = postSnapshot.key // return file name
//                    println(">>>>>>>> pdf value $url")
//                    // showPdfFromUri(url);
//                    itemsList.add(fileName)
//                    urlList.add(url)
//
//
//                    //  (ViewAssignmentFileAdapter)recyclerView.getAdapter().u
//
//
//                    //Toast.makeText(ViewAssignmentActivity.this, "Updated ", Toast.LENGTH_LONG).show();
//                }
//                // showPdfFromUri(urlList.get(0));
//                setAdapter(itemsList, urlList)
//                println(">>>>>>>> item list value $itemsList$urlList")
//
////                //creating adapter
////                adapter = new MyAdapter(getApplicationContext(), uploads);
////
////                //adding adapter to recyclerview
////                recyclerView.setAdapter(adapter);
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                progressDialog.dismiss()
//                Toast.makeText(this@ViewAssignmentActivity, "Failed to load ", Toast.LENGTH_LONG)
//                    .show()
//            }
//        })
//    }

}