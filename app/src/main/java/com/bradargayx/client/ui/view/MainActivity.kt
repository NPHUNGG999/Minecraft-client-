package com.bradargayx.client.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bradargayx.client.R
import com.bradargayx.client.manager.AutoSetupManager
import com.bradargayx.client.manager.GameLaunchManager

class MainActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView
    private lateinit var progressSetup: ProgressBar
    private lateinit var btnPlay: Button

    private lateinit var setupManager: AutoSetupManager
    private lateinit var launchManager: GameLaunchManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupManager = AutoSetupManager(this)
        launchManager = GameLaunchManager(this)

        btnPlay.setOnClickListener {
            btnPlay.isEnabled = false
            if (setupManager.isSetupCompleted()) {
                updateStatus(getString(R.string.status_launching))
                startLaunch()
            } else {
                updateStatus(getString(R.string.status_setting_up))
                startSetup()
            }
        }

        if (setupManager.isSetupCompleted()) {
            showPlayButton()
        } else {
            showSetupScreen()
            startSetup()
        }
    }

    private fun bindViews() {
        tvStatus = findViewById(R.id.tv_status)
        progressSetup = findViewById(R.id.progress_setup)
        btnPlay = findViewById(R.id.btn_play)
    }

    private fun startSetup() {
        setupManager.startSetup(
            onProgress = { message, progress ->
                runOnUiThread {
                    updateStatus(message)
                    progressSetup.progress = progress
                }
            },
            onComplete = { success ->
                runOnUiThread {
                    if (success) {
                        showPlayButton()
                    } else {
                        updateStatus(getString(R.string.status_error))
                        btnPlay.isEnabled = true
                    }
                }
            }
        )
    }

    private fun startLaunch() {
        launchManager.launchGame { success, message ->
            runOnUiThread {
                if (success) {
                    updateStatus(message)
                } else {
                    btnPlay.isEnabled = true
                    updateStatus(message)
                }
            }
        }
    }

    private fun showSetupScreen() {
        progressSetup.visibility = View.VISIBLE
        btnPlay.visibility = View.GONE
        tvStatus.text = getString(R.string.status_setting_up)
    }

    private fun showPlayButton() {
        progressSetup.visibility = View.GONE
        btnPlay.visibility = View.VISIBLE
        btnPlay.isEnabled = true
        tvStatus.text = getString(R.string.status_ready)
    }

    private fun updateStatus(message: String) {
        tvStatus.text = message
        Log.d("MainActivity", message)
    }
}
