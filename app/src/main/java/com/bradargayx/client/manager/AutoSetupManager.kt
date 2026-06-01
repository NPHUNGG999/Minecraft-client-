package com.bradargayx.client.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AutoSetupManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("braDargayX_prefs", Context.MODE_PRIVATE)
    private val modsDir: File
        get() = File(context.filesDir, ".minecraft/mods").apply { if (!exists()) mkdirs() }

    companion object {
        private const val TAG = "AutoSetupManager"
        private const val KEY_SETUP_DONE = "setup_done"
        private const val ASSET_BASE_PATH = "mods"
        private const val MOD_BRADARGAYX = "bradargayx.jar"
        private const val MOD_FABRIC = "fabric-api.jar"
    }

    fun isSetupCompleted(): Boolean {
        return prefs.getBoolean(KEY_SETUP_DONE, false)
    }

    fun startSetup(onProgress: (String, Int) -> Unit, onComplete: (Boolean) -> Unit) {
        if (isSetupCompleted()) {
            onComplete(true)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    onProgress(context.getString(com.bradargayx.client.R.string.status_setting_up), 15)
                }
                copyAssetToMods(MOD_BRADARGAYX)
                withContext(Dispatchers.Main) {
                    onProgress(context.getString(com.bradargayx.client.R.string.status_extracting_mods), 50)
                }
                copyAssetToMods(MOD_FABRIC)
                withContext(Dispatchers.Main) {
                    onProgress(context.getString(com.bradargayx.client.R.string.status_extracting_mods), 85)
                }

                prefs.edit().putBoolean(KEY_SETUP_DONE, true).apply()
                withContext(Dispatchers.Main) {
                    onProgress(context.getString(com.bradargayx.client.R.string.status_ready), 100)
                }

                withContext(Dispatchers.Main) {
                    onComplete(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Setup error", e)
                withContext(Dispatchers.Main) {
                    onComplete(false)
                }
            }
        }
    }

    private fun copyAssetToMods(fileName: String) {
        try {
            val input = context.assets.open("$ASSET_BASE_PATH/$fileName")
            val outFile = File(modsDir, fileName)
            FileOutputStream(outFile).use { output ->
                val buffer = ByteArray(8 * 1024)
                var read = input.read(buffer)
                while (read != -1) {
                    output.write(buffer, 0, read)
                    read = input.read(buffer)
                }
                output.flush()
            }
            input.close()
            Log.d(TAG, "Copied asset $fileName to ${outFile.absolutePath}")
        } catch (e: IOException) {
            Log.e(TAG, "Error copying asset $fileName", e)
            throw e
        }
    }
}
