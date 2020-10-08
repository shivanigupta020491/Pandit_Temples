package com.indtrack.pandit_temples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.indtrack.pandit_temples.R
import com.indtrack.pandit_temples.pojo.TemplePojo
import com.indtrack.pandit_temples.pojo.mythologicalPojo
import java.util.ArrayList

class MythologicalDataAdapter(var listdata: ArrayList<mythologicalPojo>) : RecyclerView.Adapter<MythologicalDataAdapter.ItemViewHolder>() {
    // private var changePosition: UploadPosition? = null
    // private var viewPosition: ViewPosition? = null

    //private AssignmentDataAdapter.GetJsonData getJsonData;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        System.out.println("pandit list in adapter ")
        System.out.println("pandit list in adapter " + listdata.size)

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.mythological_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        //DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault());

        // for change color of row
//        if (position % 2 == 0){
//            itemViewHolder.relativeL.setBackgroundResource(R.color.green1);
//        }else {
//            itemViewHolder.relativeL.setBackgroundResource(R.color.dashboard_heading3);
//        }
        System.out.println("pandit list in adapter " + listdata[position])

        itemViewHolder.topicText.text = listdata[position].title
        itemViewHolder.descText.text = listdata[position].description
        //itemViewHolder.dateText.text = listdata[position].date


//            itemViewHolder.dateTextView.text = "Description "
//            itemViewHolder.activityTextView.text = "Work "

        // for upload button click page
//        itemViewHolder.uploadBtn!!.setOnClickListener {
//            if (changePosition != null) {
//                changePosition!!.onclick(position)
//            }
//        }
//        itemViewHolder.viewBtn!!.setOnClickListener {
//            if (viewPosition != null) {
//                viewPosition!!.onclick(position)
//            }
//        }
    }

    //    @Override
    //    public int getItemCount() {
    //        return pendinglist.length();
    //    }
    override fun getItemCount(): Int {
        return listdata.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //   public CircleImageView imageTextimageView;
        var topicText: TextView
        var dateText: TextView
        var descText: TextView
        var relativeL: RelativeLayout


        init {
            topicText = itemView.findViewById<View>(R.id.assignmentTopicName) as TextView
            dateText = itemView.findViewById<View>(R.id.dateText) as TextView
            descText = itemView.findViewById<View>(R.id.descTV) as TextView
            relativeL = itemView.findViewById<View>(R.id.relativeLayoutAssign) as RelativeLayout
        }
    }

//    fun uploadItemPosition(changePosition: UploadPosition?) {
//        this.changePosition = changePosition
//    }
//
//    interface UploadPosition {
//        fun onclick(pos: Int)
//    }
//
//    fun viewItemPosition(viewPosition: ViewPosition?) {
//        this.viewPosition = viewPosition
//    }
//
//    interface ViewPosition {
//        fun onclick(pos: Int)
//    }

//    override fun getFilter(): Filter {
//        return exampleFilter
//    }

//    private val exampleFilter: Filter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence): FilterResults {
//            var filteredList = JSONArray()
//            if (constraint == null || constraint.length == 0) {
//                filteredList = additionalList
//            } else {
//                val filterPattern =
//                    constraint.toString().toLowerCase().trim { it <= ' ' }
//                try {
//                    for (i in 0 until additionalList.length()) {
//                        if ((additionalList.get(i) as JSONObject).getString("param3").toLowerCase()
//                                .contains(filterPattern)
//                        ) {
//                            filteredList.put(additionalList.get(i))
//                        }
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        override fun publishResults(
//            constraint: CharSequence,
//            results: FilterResults
//        ) {
//            var filteredlist = JSONArray()
//            filteredlist = results.values as JSONArray
//            pendinglist = filteredlist
//            notifyDataSetChanged()
//        }
//    }
//
//    fun setJsonData(getJsonData: GetJsonData) {
//        getJsonData = getJsonData
//    }
//
//    interface GetJsonData {
//        fun onClick(jsonObject: JSONObject?)
//    }


}