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

/**
 * MainActivity.kt
 * 
 * Mục đích: Activity chính của BraDargayX Launcher
 * - Hiển thị logo & status text
 * - Manage setup process (copy mods)
 * - Hiển thị nút PLAY khi setup xong
 * - Khởi chạy game khi nhấn PLAY
 * 
 * Thư mục: app/src/main/java/com/bradargayx/client/ui/view/MainActivity.kt
 */

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    // ===== UI Components =====
    private lateinit var tvStatus: TextView
    private lateinit var progressSetup: ProgressBar
    private lateinit var btnPlay: Button
    private lateinit var statusContainer: View

    // ===== Managers =====
    private lateinit var setupManager: AutoSetupManager
    private lateinit var launchManager: GameLaunchManager

    // ===== State =====
    private var isSetupCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Đặt layout
        setContentView(R.layout.activity_main)

        // Khởi tạo managers
        setupManager = AutoSetupManager(this)
        launchManager = GameLaunchManager(this)

        // Bind UI components
        bindViews()

        // Thiết lập listener cho nút PLAY
        setupPlayButtonListener()

        // Bắt đầu setup (nếu chưa setup lần nào)
        startSetupIfNeeded()
    }

    /**
     * Bind tất cả UI components từ layout
     */
    private fun bindViews() {
        tvStatus = findViewById(R.id.tv_status)
        progressSetup = findViewById(R.id.progress_setup)
        btnPlay = findViewById(R.id.btn_play)
        statusContainer = findViewById(R.id.status_container)
    }

    /**
     * Thiết lập listener cho nút PLAY
     */
    private fun setupPlayButtonListener() {
        btnPlay.setOnClickListener {
            Log.i(TAG, "PLAY button clicked")
            
            // Vô hiệu hóa nút trong lúc khởi chạy
            btnPlay.isEnabled = false
            
            // Cập nhật status
            updateStatus("Launching game...")
            
            // Gọi GameLaunchManager để khởi chạy game
            launchManager.launchGame { success, message ->
                Log.i(TAG, "Launch callback: success=$success, message=$message")
                
                if (success) {
                    // Game đã khởi chạy, có thể finish activity
                    // hoặc hiển thị message "Game is running..."
                    updateStatus("Game is running...")
                } else {
                    // Khôi phục nút nếu lỗi
                    btnPlay.isEnabled = true
                    updateStatus("Error: $message")
                }
            }
        }
    }

    /**
     * Bắt đầu setup nếu app mới chạy lần đầu
     */
    private fun startSetupIfNeeded() {
        if (setupManager.isSetupCompleted()) {
            // Setup đã xong, hiển thị nút PLAY ngay
            Log.i(TAG, "Setup already completed, showing PLAY button")
            showPlayButton()
        } else {
            // Setup chưa xong, bắt đầu setup
            Log.i(TAG, "Setup not completed, starting setup process")
            showSetupUI()
            startSetup()
        }
    }

    /**
     * Hiển thị UI setup (progress bar + status text)
     */
    private fun showSetupUI() {
        // Ẩn nút PLAY
        btnPlay.visibility = View.GONE
        
        // Hiển thị progress bar
        progressSetup.visibility = View.VISIBLE
        
        // Cập nhật status text
        updateStatus("Đang thiết lập BraDargayX...")
    }

    /**
     * Hiển thị nút PLAY (setup xong)
     */
    private fun showPlayButton() {
        // Ẩn progress bar
        progressSetup.visibility = View.GONE
        
        // Hiển thị nút PLAY
        btnPlay.visibility = View.VISIBLE
        btnPlay.isEnabled = true
        
        // Cập nhật status text
        updateStatus("Ready to Launch")
        
        isSetupCompleted = true
    }

    /**
     * Bắt đầu quá trình setup
     * - Copy bradargayx.jar
     * - Copy fabric-api.jar
     * - Hiển thị progress
     */
    private fun startSetup() {
        setupManager.startSetup(
            onProgress = { message, progress ->
                // Chạy trên main thread
                runOnUiThread {
                    Log.i(TAG, "Setup progress: $message ($progress%)")
                    updateStatus(message)
                    progressSetup.progress = progress
                }
            },
            onComplete = { success ->
                // Chạy trên main thread
                runOnUiThread {
                    if (success) {
                        Log.i(TAG, "Setup completed successfully")
                        showPlayButton()
                    } else {
                        Log.e(TAG, "Setup failed")
                        showSetupError()
                    }
                }
            }
        )
    }

    /**
     * Hiển thị lỗi setup
     */
    private fun showSetupError() {
        // Ẩn progress bar
        progressSetup.visibility = View.GONE
        
        // Hiển thị error message
        updateStatus("Setup failed. Please check permissions and restart.")
        
        // Có thể thêm nút Retry
        btnPlay.apply {
            text = "Retry"
            visibility = View.VISIBLE
            isEnabled = true
            setOnClickListener {
                // Reset setup và thử lại
                setupManager.resetSetup()
                recreate() // Restart activity
            }
        }
    }

    /**
     * Cập nhật status text
     */
    private fun updateStatus(message: String) {
        tvStatus.text = message
        Log.i(TAG, "Status updated: $message")
    }

    /**
     * Override onBackPressed để tránh exit khi setup
     */
    override fun onBackPressed() {
        if (!isSetupCompleted) {
            // Setup chưa xong, không cho back
            Log.i(TAG, "Back button blocked during setup")
            return
        }
        
        super.onBackPressed()
    }
}
