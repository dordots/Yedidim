package com.startach.yedidim

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.constraint.Group
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.startach.yedidim.Model.Event
import com.startach.yedidim.Model.displayableCase
import com.startach.yedidim.modules.App
import com.startach.yedidim.modules.eventinfoactivity.EventInfoActivityModule
import com.startach.yedidim.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.Locale.ENGLISH
import javax.inject.Inject


class EventInfoActivity : AppCompatActivity() {
    lateinit var cevent : Event

    companion object {

        private val EXTRAS_EVENT = "event"

        fun createIntent(context: Context, event: Event): Intent {
            val intent = Intent(context, EventInfoActivity::class.java)
            intent.putExtra(EXTRAS_EVENT, event)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }

        private fun extractEvent(intent: Intent): Event {
            return intent.extras[EXTRAS_EVENT] as Event
        }
    }

    @BindView(R.id.event_type) lateinit var eventType: TextView
    @BindView(R.id.car) lateinit var car: TextView
    @BindView(R.id.address) lateinit var address: TextView
    @BindView(R.id.details) lateinit var details: TextView
    @BindView(R.id.name) lateinit var name: TextView
    @BindView(R.id.phone) lateinit var phone: TextView
    @BindView(R.id.group_extended_details) lateinit var extendedDetailsGroup: Group
    @BindView(R.id.group_basic_details) lateinit var basicDetailsGroup: Group

    @Inject lateinit var vm: EventInfoViewModel
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        ButterKnife.bind(this)

        (application as App).component
                .newEventInfoActivitySubComponent(EventInfoActivityModule(this))
                .inject(this)
        val event = extractEvent(intent)
        cevent = event
        Timber.d("event = " + event)
        vm.bindViewModel(event)

        extendedDetailsGroup.visibility = View.GONE
        basicDetailsGroup.visibility = View.VISIBLE
        disposables += vm.eventLoadedObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    address.text = it.details?.address
                    name.text = it.details?.callerName
                    phone.text = it.details?.phoneNumber
                    details.text = it.details?.more
                    eventType.text = it.displayableCase(resources)
                    car.text = it.details?.carType
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    @OnClick(R.id.btn_navigate)
    fun navigate() {
        val uri = String.format(ENGLISH, "geo:%f,%f", cevent.details?.geo?.lat, cevent.details?.geo?.lon)
        val intentNav = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(Intent.createChooser(intentNav, "Select your maps app"))

        val intent = Intent(this,ChatHeadService::class.java)
        intent.putExtra(EXTRAS_EVENT, cevent)
        startService(intent)
        finish()
    }

    @OnClick(R.id.btn_call)
    fun call() {

    }

    @OnClick(R.id.btn_cancel_event)
    fun cancelEvent() {
        vm.cancelEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish,
                        { t -> handleError(t, R.string.event_info_close_event_error, "cant cancel event") }
                )
    }

    @OnClick(R.id.btn_close_event)
    fun closeEvent() {
        vm.closeEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish,
                        { t -> handleError(t, R.string.event_info_close_event_error, "cant close event") }
                )
    }

    @OnClick(R.id.btn_ignore_event)
    fun ignoreEvent() {
        vm.ignoreEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish)
    }

    @OnClick(R.id.btn_take_event)
    fun takeEvent() {
        AlertDialog.Builder(this)
                .setTitle(R.string.event_info_take_event_title)
                .setMessage(R.string.event_info_take_event_message)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    vm.takeEvent()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ res ->
                                if (!res) {
                                    AlertDialog.Builder(this)
                                            .setTitle(R.string.event_info_event_taken_title)
                                            .setMessage(R.string.event_info_event_taken_message)
                                            .setIcon(R.drawable.ic_menu_gallery)
                                            .setPositiveButton(R.string.dialog_close, { dialog, which ->
                                                dialog.dismiss()
                                            })
                                            .show()
                                } else {
                                    extendedDetailsGroup.visibility = View.VISIBLE
                                    basicDetailsGroup.visibility = View.GONE
                                }
                            },
                                    { t -> handleError(t, R.string.event_info_take_event_error, "cant take event") }
                            )
                    dialog.dismiss()
                })
                .setNegativeButton(android.R.string.cancel, { dialog, which ->
                    dialog.cancel()
                })
                .show()

    }

    private fun handleError(t: Throwable, @StringRes msg: Int, log: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        Timber.d(t, log)
    }
}