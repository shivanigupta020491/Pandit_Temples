package com.indtrack.pandit_temples.ui.pandit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.indtrack.pandit_temples.pojo.TemplePojo
import org.json.JSONObject

class PanditViewModel : ViewModel() {
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    var templeList:ArrayList<TemplePojo> = ArrayList()

    var _text = MutableLiveData<ArrayList<TemplePojo>>()

    public fun getUser():LiveData<ArrayList<TemplePojo>>{
        System.out.println(">>>>>>>pandit detail list of 1 " + _text)
        if (_text == null){
            _text = MutableLiveData<ArrayList<TemplePojo>>()
           getDataFromFirebase()
        }
        System.out.println(">>>>>>>>>pandit detail list of 2 " + _text)
        return _text
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

                    templeList.add(templePojo)
                    postSnapshot.children.forEach {

                        System.out.println(">>>>>>>>pandit detail list 1 " + it.value)

                    }


                    templeList.add(templePojo)
                }

                _text.postValue(templeList)

                addRecyclerview(templeList);
            }

        })


        System.out.println("pandit detail list " + templeList)



    }

    private fun addRecyclerview(templeList: ArrayList<TemplePojo>) {
     val _text = MutableLiveData<ArrayList<TemplePojo>>().apply {
            value = templeList

         val text: LiveData<ArrayList<TemplePojo>> = _text
        }

    }

    class PanditRepository(){

    }
}