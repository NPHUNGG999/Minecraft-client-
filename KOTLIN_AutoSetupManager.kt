package com.bradargayx.client.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.*
import java.io.*

/**
 * AutoSetupManager.kt
 * 
 * Mục đích: Quản lý việc thiết lập ban đầu ứng dụng
 * - Kiểm tra lần đầu chạy app
 * - Copy bradargayx.jar và fabric-api.jar từ assets → ~/.minecraft/mods
 * - Hiển thị progress bar
 * - Gọi callback khi hoàn thành
 * 
 * Thư mục: app/src/main/java/com/bradargayx/client/manager/AutoSetupManager.kt
 */

class AutoSetupManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BraDargayX_Prefs", Context.MODE_PRIVATE)
    
    private val modsDir: File
        get() = File(context.filesDir, ".minecraft/mods").apply {
            if (!exists()) {
                mkdirs()
            }
        }

    companion object {
        const val PREF_SETUP_COMPLETED = "setup_completed"
        const val PREF_SETUP_VERSION = "setup_version"
        const val MOD_JAR_BRADARGAYX = "bradargayx.jar"
        const val MOD_JAR_FABRIC = "fabric-api.jar"
        const val TAG = "AutoSetupManager"
    }

    /**
     * Kiểm tra xem setup đã hoàn thành hay chưa
     */
    fun isSetupCompleted(): Boolean {
        return sharedPreferences.getBoolean(PREF_SETUP_COMPLETED, false)
    }

    /**
     * Bắt đầu quá trình setup
     * - Copy 2 file .jar từ assets vào mods folder
     * - Chạy trên background thread (Coroutine)
     * - Gọi callback khi xong
     */
    fun startSetup(
        onProgress: (message: String, progress: Int) -> Unit,
        onComplete: (success: Boolean) -> Unit
    ) {
        // Nếu đã setup rồi thì bỏ qua
        if (isSetupCompleted()) {
            onComplete(true)
            return
        }

        // Chạy setup trên background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                onProgress("Đang khởi tạo BraDargayX...", 10)
                
                // Step 1: Copy bradargayx.jar
                onProgress("Đang copy bradargayx.jar...", 30)
                copyModFromAssets(MOD_JAR_BRADARGAYX)
                
                // Step 2: Copy fabric-api.jar
                onProgress("Đang copy fabric-api.jar...", 60)
                copyModFromAssets(MOD_JAR_FABRIC)
                
                // Step 3: Hoàn thành
                onProgress("Thiết lập hoàn tất!", 100)
                
                // Lưu vào SharedPreferences
                sharedPreferences.edit().apply {
                    putBoolean(PREF_SETUP_COMPLETED, true)
                    putString(PREF_SETUP_VERSION, "1.0.0")
                    apply()
                }
                
                Log.i(TAG, "Setup completed successfully")
                
                // Gọi callback trên main thread
                withContext(Dispatchers.Main) {
                    onComplete(true)
                }

            } catch (e: Exception) {
                Log.e(TAG, "Setup failed: ${e.message}", e)
                
                withContext(Dispatchers.Main) {
                    onComplete(false)
                }
            }
        }
    }

    /**
     * Copy file .jar từ assets vào mods folder
     * Xử lý streaming để tiết kiệm RAM với file lớn
     */
    private fun copyModFromAssets(fileName: String) {
        try {
            val assetsDir = "mods"
            val inputStream: InputStream = context.assets.open("$assetsDir/$fileName")
            val outputFile = File(modsDir, fileName)

            // Xóa file cũ nếu tồn tại
            if (outputFile.exists()) {
                outputFile.delete()
            }

            val outputStream = FileOutputStream(outputFile)

            // Streaming copy (tiết kiệm RAM)
            inputStream.use { input ->
                outputStream.use { output ->
                    val buffer = ByteArray(8192) // 8KB buffer
                    var bytesRead: Int
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                    }
                }
            }

            Log.i(TAG, "Successfully copied $fileName to ${outputFile.absolutePath}")

        } catch (e: FileNotFoundException) {
            Log.e(TAG, "File not found: $fileName. Make sure it's in app/src/main/assets/mods/", e)
            throw e
        } catch (e: IOException) {
            Log.e(TAG, "IOException while copying $fileName: ${e.message}", e)
            throw e
        }
    }

    /**
     * Lấy đường dẫn thư mục .minecraft
     */
    fun getMinecraftDir(): File {
        return File(context.filesDir, ".minecraft").apply {
            if (!exists()) mkdirs()
        }
    }

    /**
     * Lấy đường dẫn thư mục mods
     */
    fun getModsDir(): File = modsDir

    /**
     * Xóa setup cũ (dùng khi cập nhật app)
     */
    fun resetSetup() {
        sharedPreferences.edit().apply {
            remove(PREF_SETUP_COMPLETED)
            remove(PREF_SETUP_VERSION)
            apply()
        }
        Log.i(TAG, "Setup reset completed")
    }
}
