package com.startach.yedidim.MainPageFragments

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.startach.yedidim.MainPageFragments.EventsFragment.OnListFragmentInteractionListener
import com.startach.yedidim.MainPageFragments.dummy.DummyContent.DummyItem
import com.startach.yedidim.Model.Event
import com.startach.yedidim.Model.displayableCase
import com.startach.yedidim.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyEventItemRecyclerViewAdapter(private val mValues: List<Event>, private val mListener: OnListFragmentInteractionListener?, val resources: Resources) : RecyclerView.Adapter<MyEventItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_eventitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mEventCase.text = mValues[position].displayableCase(resources)
        holder.mEventAddress.text = mValues[position].details?.address
        holder.timeDate.text = convertTime(mValues[position].timestamp)?:"No time"

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    fun convertTime(time: Long?): String? {
        val date = time?.let { Date(it) }
        val format = SimpleDateFormat("dd/MM/yy HH:mm")
        return format.format(date)
    }
    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mEventCase: TextView
        val mEventAddress: TextView
        var mItem: Event? = null
        val timeDate: TextView

        init {
            mEventCase = mView.findViewById(R.id.event_case) as TextView
            mEventAddress = mView.findViewById(R.id.event_address) as TextView
            timeDate = mView.findViewById(R.id.time) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mEventAddress.text + "'"
        }
    }
}
