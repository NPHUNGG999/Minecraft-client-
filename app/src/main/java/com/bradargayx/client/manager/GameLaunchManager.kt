package com.bradargayx.client.manager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

class GameLaunchManager(private val context: Context) {
    companion object {
        const val FORCED_VERSION_ID = "fabric-loader-0.16.x-1.21.1"
    }

    fun launchGame(onComplete: (Boolean, String) -> Unit) {
        Log.i("GameLaunchManager", "Launching fixed version: $FORCED_VERSION_ID")

        Handler(Looper.getMainLooper()).postDelayed({
            onComplete(true, context.getString(com.bradargayx.client.R.string.status_launching))
        }, 800)
    }
}
