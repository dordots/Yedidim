package com.startach.yedidim.MainPageFragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.startach.yedidim.EventInfoActivity
import com.startach.yedidim.EventState
import com.startach.yedidim.Model.Event
import com.startach.yedidim.R
import com.startach.yedidim.modules.App
import com.startach.yedidim.modules.eventfragment.EventsFragmentModule
import com.startach.yedidim.network.EventApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class EventsFragment : Fragment() {

    val disposables = CompositeDisposable()

    @Inject
    lateinit var eventapi : EventApi

    @BindView(R.id.loding_progress)
    lateinit var progressBar : ProgressBar

    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ((activity as Activity).applicationContext as App).component
                .newEventsFragmentSubComponent(EventsFragmentModule(this))
                .inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_eventitem_list, container, false)
        ButterKnife.bind(this,view)

        val recycler = view.findViewById(R.id.events_list) as RecyclerView

        disposables.add(eventapi.listOfEvents()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { data ->
                    progressBar.visibility = View.GONE

                    Timber.d(data.size.toString())
                    val currentActivity = activity
                    if (currentActivity != null) {
                        val context = view.getContext()
                        recycler.layoutManager = LinearLayoutManager(context)
                        recycler.adapter = MyEventItemRecyclerViewAdapter(data.toList(), mListener, resources)
                    }
                } ?: Disposables.empty())

        return view
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            mListener = object : OnListFragmentInteractionListener {
                override fun onListFragmentInteraction(item: Event) {
                    val intent = EventInfoActivity.createIntent(activity,item,EventState.PreDecision)
                    startActivity(intent)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Event)
    }

}
