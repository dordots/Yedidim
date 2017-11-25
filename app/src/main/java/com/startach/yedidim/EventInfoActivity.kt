package com.startach.yedidim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.startach.yedidim.Model.Event
import timber.log.Timber

class EventInfoActivity : AppCompatActivity() {

    companion object {

        private val EXTRAS_EVENT = "event"

        fun createIntent(context: Context, event: Event): Intent {
            val intent = Intent(context, EventInfoActivity::class.java)
            intent.putExtra(EXTRAS_EVENT, event)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        Timber.d("event = " + (intent.extras[EXTRAS_EVENT] as Event))
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}

