package com.indtrack.pandit_temples.ui.temple_detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.indtrack.pandit_temples.R
import com.indtrack.pandit_temples.TempleActivity
import com.indtrack.pandit_temples.adapter.TempleDataAdapter
import com.indtrack.pandit_temples.pojo.TemplePojo
import com.indtrack.pandit_temples.ui.pandit.PanditViewModel
import com.indtrack.pandit_temples.ui.temple.TempleViewModel
import java.text.SimpleDateFormat
import java.util.*


class TempleDetailFragment : Fragment(), View.OnClickListener {

    private var searchView: SearchView? = null
    private var recyclerView: RecyclerView? = null
    private var noDataShowLayout: LinearLayout? = null
    private var listShowLayout: LinearLayout? = null
    private lateinit var templeViewModel: TempleViewModel
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var name: EditText
    private lateinit var location: EditText
    private lateinit var activity: EditText
    private lateinit var date: TextView
    private lateinit var progressBar: ProgressBar
    private var builder: AlertDialog.Builder? = null
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var fabButton: FloatingActionButton? = null
    var templeList:ArrayList<TemplePojo> = ArrayList()
    var adapter: TempleDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_temple_detail, container, false)
        swipeRefreshLayout = root.findViewById(R.id.swipeAssign)
        searchView = root.findViewById(R.id.assignmentTeacherSearc)
        recyclerView = root.findViewById(R.id.recyclerListAssignment)
        noDataShowLayout = root.findViewById(R.id.dataLayoutAssign)
        listShowLayout = root.findViewById(R.id.listShowLayoutAssign)
        fabButton = root.findViewById(R.id.addAssignmentButton)

        initInstance()
        setListener()
       // getDataFromFirebase()
        return root
    }
    private fun initInstance() {


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
        mDatabaseReference!!.setValue(
            TemplePojo(name.text.toString(),location.text.toString(),
            activity.text.toString(),date.text.toString())
        )


    }

    private fun getDataFromFirebase(){
        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.reference

        val myTopPostsQuery: Query = mDatabaseReference!!.child("Pandit")
        myTopPostsQuery!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    System.out.println(">>>>>>>  postSnapshot " + dataSnapshot)

                    var templeValue1:Map<String,String> = postSnapshot.value as Map<String,String>
                    System.out.println(">>>>>>>  postSnapshot key 2" + templeValue1.get("location"))

                    var templePojo = TemplePojo(templeValue1.get("name"),templeValue1.get("location"),
                        templeValue1.get("date"),templeValue1.get("activity"))

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

        adapter = TempleDataAdapter(templeList,"TempleDetailFragment")
        recyclerView!!.layoutManager = LinearLayoutManager(getActivity())
        recyclerView!!.adapter = adapter
        recyclerView!!.setHasFixedSize(false)
        adapter!!.notifyDataSetChanged()
    }

}