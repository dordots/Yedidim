package com.startach.yedidim.modules.log;


import android.util.Log;

import timber.log.Timber;

public class ReleaseTimberTree extends Timber.DebugTree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return priority > Log.DEBUG;
    }
}
