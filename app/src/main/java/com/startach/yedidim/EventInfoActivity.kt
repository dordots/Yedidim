package com.startach.yedidim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.Group
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import com.startach.yedidim.Model.Event
import com.startach.yedidim.Model.displayableCase
import com.startach.yedidim.modules.App
import com.startach.yedidim.modules.eventinfoactivity.EventInfoActivityModule
import com.startach.yedidim.utils.plusAssign
import com.startach.yedidim.utils.showDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


class EventInfoActivity : AppCompatActivity(), EventInfoViewModel.Inputs {

    companion object {

        private val EXTRAS_EVENT = "event"
        private val EXTRAS_EVENT_STATE = "event_state"

        fun createIntent(context: Context, event: Event, eventState: EventState = EventState.PreDecision): Intent {
            return Intent(context, EventInfoActivity::class.java).apply {
                val extrasBundle = Bundle()
                extrasBundle.putParcelable(EXTRAS_EVENT, event)
                extrasBundle.putSerializable(EXTRAS_EVENT_STATE, eventState)
                putExtras(extrasBundle)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }

        private fun extractEvent(intent: Intent): Event =
                intent.extras.getParcelable(EXTRAS_EVENT) as Event

        private fun extractEventState(intent: Intent): EventState =
                intent.extras.getSerializable(EXTRAS_EVENT_STATE) as EventState

    }

    @BindView(R.id.event_type) lateinit var eventType: TextView
    @BindView(R.id.car) lateinit var car: TextView
    @BindView(R.id.address) lateinit var address: TextView
    @BindView(R.id.details) lateinit var details: TextView
    @BindView(R.id.name) lateinit var name: TextView
    @BindView(R.id.phone) lateinit var phone: TextView
    @BindView(R.id.group_extended_details) lateinit var extendedDetailsGroup: Group
    @BindView(R.id.group_basic_details) lateinit var basicDetailsGroup: Group
    @BindView(R.id.group_progress) lateinit var progressGroup: Group

    @BindView(R.id.btn_cancel_event) lateinit var cancelBtn: View
    @BindView(R.id.btn_close_event) lateinit var closeBtn: View
    @BindView(R.id.btn_ignore_event) lateinit var ignoreBtn: View
    @BindView(R.id.btn_take_event) lateinit var takeBtn: View
    @BindView(R.id.btn_navigate) lateinit var navigateBtn: View
    @BindView(R.id.btn_call) lateinit var callButton: View


    @Inject lateinit var vm: EventInfoViewModel
    private val disposables: CompositeDisposable = CompositeDisposable()
    lateinit var extenalEventStatePublishSubject : Observable<EventState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        ButterKnife.bind(this)

        (application as App).component
                .newEventInfoActivitySubComponent(EventInfoActivityModule(this))
                .inject(this)
        val event = extractEvent(intent)
        val eventState = extractEventState(intent)
        extenalEventStatePublishSubject = Observable.just(eventState)
        Timber.d("event = " + event)
        vm.bindViewModel(event, this)

        disposables += vm.event()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    address.text = it.details?.address
                    name.text = it.details?.callerName
                    phone.text = it.details?.phoneNumber
                    details.text = it.details?.more
                    eventType.text = it.displayableCase(resources)
                    car.text = it.details?.carType
                }
        progressGroup.visibility = View.GONE
        extendedDetailsGroup.visibility = View.GONE
        basicDetailsGroup.visibility = View.VISIBLE
        disposables += vm.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        is AlreadyTakenState -> {
                            progressGroup.visibility = View.GONE
                            AlertDialog.Builder(this)
                                    .setTitle(R.string.event_info_event_taken_title)
                                    .setMessage(R.string.event_info_event_taken_message)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                        }
                        is ProcessingState -> {
                            progressGroup.visibility = View.VISIBLE
                        }
                        is ExitState -> {
                            this.moveTaskToBack(false)
                            finish()
                        }
                        is HandlingState -> {
                            progressGroup.visibility = View.GONE
                            extendedDetailsGroup.visibility = View.VISIBLE
                            basicDetailsGroup.visibility = View.GONE
                        }
                        is ErrorState -> {
                            progressGroup.visibility = View.GONE
                            Toast.makeText(this, R.string.event_info_error_msg, Toast.LENGTH_LONG).show()
                            Timber.d(state.throwable, "error occurred with operation = {${state.operation}}")
                        }
                    }

                }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun takeEvent(): Observable<Any> {
        return RxView.clicks(takeBtn)
                .mergeWith(extenalEventStatePublishSubject.filter { it == EventState.Take })
                .showDialog(this, R.string.event_info_take_event_title, R.string.event_info_take_event_message)
    }

    override fun cancelEvent(): Observable<Any> {
        return RxView.clicks(cancelBtn)
                .showDialog(this, R.string.event_info_cance_title, R.string.event_info_cancel_message)
    }

    override fun closeEvent(): Observable<Any> {
        return RxView.clicks(closeBtn)
                .showDialog(this, R.string.event_info_close_title, R.string.event_info_close_message)
    }

    override fun ignoreEvent(): Observable<Any> {
        return RxView.clicks(ignoreBtn)
                .mergeWith(extenalEventStatePublishSubject.filter { it == EventState.GiveUp })
                .showDialog(this, R.string.event_info_ignore_title, R.string.event_info_ignore_message)
    }

    override fun navigate(): Observable<Any> = RxView.clicks(navigateBtn)

    override fun call(): Observable<Any> = RxView.clicks(callButton)

    override fun returnHandled(): Observable<Any> =
            Observable.never<Any>()
                    .mergeWith(extenalEventStatePublishSubject.filter { it == EventState.Handling })
}

enum class EventState {
    PreDecision,
    Take,
    GiveUp,
    Handling
}
