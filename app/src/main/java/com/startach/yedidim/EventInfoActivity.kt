package com.startach.yedidim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.startach.yedidim.Model.Event
import com.startach.yedidim.modules.App
import com.startach.yedidim.modules.eventinfoactivity.EventInfoActivityModule
import com.startach.yedidim.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class EventInfoActivity : AppCompatActivity() {

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

    @BindView(R.id.address) lateinit var address: TextView
    @BindView(R.id.more) lateinit var more: TextView
    @BindView(R.id.callername) lateinit var callername: TextView
    @BindView(R.id.phonenumber) lateinit var phonenumber: TextView

    @Inject lateinit var vm: EventInfoViewModel
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        ButterKnife.bind(this)

        (application as App).component
                .newEventInfoActivitySubComponent(EventInfoActivityModule(this))
                .inject(this)
        vm.bindViewModel(extractEvent(intent))

        disposables += vm.eventLoadedObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    address.text = event.details.address
                    callername.text = event.details.callerName
                    phonenumber.text = event.details.phoneNumber
                    more.text = event.details.more
                }

        Timber.d("event = " + (intent.extras[EXTRAS_EVENT] as Event))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    @OnClick(R.id.navigateBtn)
    fun navigate() {

    }

    @OnClick(R.id.cancelEvent)
    fun cancelEvent() {

    }

    @OnClick(R.id.takeEvent)
    fun takeEvent() {

    }
}

