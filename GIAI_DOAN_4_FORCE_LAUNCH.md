# 🎮 GIAI ĐOẠN 4 CHI TIẾT: KHÓA CẤU HÌNH KHỞI CHẠY

## PHẦN 1: TÌM KIẾM FILE LOGIC KHỞI CHẠY

### 1.1 Cấu Trúc PojavLauncher Gốc

Trong fork PojavLauncher của bạn, cấu trúc thường như sau:

```
app/src/main/java/net/kdt/pojavlaunch/
├── MainActivity.kt                          ← Có thể là nơi bắt đầu
├── LauncherActivity.kt                      ← Hoặc ở đây
├── GameLaunchActivity.kt                    ← Hoặc ở đây
├── ui/
│   ├── activities/
│   │   ├── VersionSelectActivity.kt         ← TÌMU KIẾM NÀY
│   │   ├── LauncherActivity.kt
│   │   └── GameLaunchActivity.kt
│   ├── views/
│   │   └── VersionSpinner.kt                ← Spinner chọn version
├── utils/
│   ├── GameLauncher.kt                      ← TÌMU KIẾM NÀY
│   └── VersionManager.kt                    ← Quản lý version
└── model/
    ├── Version.kt
    └── GameConfig.kt
```

### 1.2 Các Lệnh Tìm Kiếm Hữu Ích

```bash
# Tìm file chứa "version" selection logic
grep -r "versionSpinner\|versionSelector" app/src --include="*.kt" --include="*.java"

# Tìm file chứa "startGame\|launchGame"
grep -r "startGame\|launchGame\|performLaunch" app/src --include="*.kt" --include="*.java"

# Tìm file chứa "fabric-loader"
grep -r "fabric-loader\|fabric" app/src --include="*.kt" --include="*.java"

# Tìm native method calls (JNI)
grep -r "nativeStart\|startGameNative\|System.loadLibrary" app/src --include="*.kt" --include="*.java"

# Tìm file chứa "SelectedItem\|getSelected"
grep -r "getSelectedItem\|selectedItem\|selected.version" app/src --include="*.kt" --include="*.java"
```

---

## PHẦN 2: PHÂN TÍCH CODE ĐIỂN HÌNH

### 2.1 Cấu Trúc Code PojavLauncher Gốc (VD)

```kotlin
// File: VersionSelectActivity.kt
class VersionSelectActivity : AppCompatActivity() {
    
    private lateinit var versionSpinner: Spinner
    private var versions: List<String> = listOf()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_select)
        
        versionSpinner = findViewById(R.id.version_spinner)
        loadVersions()
        
        val playButton = findViewById<Button>(R.id.btn_play)
        playButton.setOnClickListener {
            // ❌ TRƯỚC: Người dùng chọn version từ spinner
            val selectedVersion = versionSpinner.selectedItem as String
            launchGame(selectedVersion)
        }
    }
    
    private fun launchGame(versionId: String) {
        // Khởi chạy game với version được chọn
        GameLauncher.launch(this, versionId)
    }
}
```

### 2.2 Cấu Trúc GameLauncher (VD)

```kotlin
// File: GameLauncher.kt
object GameLauncher {
    
    fun launch(context: Context, versionId: String) {
        Log.i("GameLauncher", "Launching game with version: $versionId")
        
        // Step 1: Validate version
        if (!versionExists(versionId)) {
            Log.e("GameLauncher", "Version not found!")
            return
        }
        
        // Step 2: Chuẩn bị game arguments
        val gameArgs = prepareGameArguments(versionId)
        val javaArgs = prepareJavaArguments()
        
        // Step 3: Gọi native method để start game
        nativeStartGame(gameArgs, javaArgs)
    }
    
    private external fun nativeStartGame(gameArgs: String, javaArgs: String)
}
```

---

## PHẦN 3: CÁCH LOCK CONFIGURATION (FORCE VERSION)

### 3.1 Phương Pháp 1: Direct Version Force (Đơn Giản)

**Thay đổi của bạn:**

```kotlin
// File: app/src/main/java/com/bradargayx/client/ui/view/MainActivity.kt

class MainActivity : AppCompatActivity() {
    
    companion object {
        // ===== FORCE VERSION =====
        const val FORCED_VERSION_ID = "fabric-loader-0.16.x-1.21.1"
    }
    
    private fun setupPlayButtonListener() {
        btnPlay.setOnClickListener {
            // Khóa cứng version, bỏ qua spinner/selection
            launchGameWithForcedVersion()
        }
    }
    
    private fun launchGameWithForcedVersion() {
        val launchManager = GameLaunchManager(this)
        
        // Trực tiếp gọi launch với version cố định
        launchManager.launchGame { success, message ->
            if (success) {
                updateStatus("Game is launching...")
                // Có thể finish activity sau vài giây
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            } else {
                updateStatus("Error: $message")
            }
        }
    }
}
```

### 3.2 Phương Pháp 2: Override PojavLauncher's Launch Method

**Nếu bạn muốn tích hợp với code gốc của PojavLauncher:**

```kotlin
// File: app/src/main/java/com/bradargayx/client/manager/PojavLaunchOverride.kt

package com.bradargayx.client.manager

/**
 * Override các method của PojavLauncher để force version
 * Cách này yêu cầu kế thừa từ GameLauncher hoặc tương tự
 */
class PojavLaunchOverride {
    
    companion object {
        const val FORCED_VERSION_ID = "fabric-loader-0.16.x-1.21.1"
        
        /**
         * Ghi đè phương thức launch mặc định của PojavLauncher
         * Thay vì nhận version từ spinner, sử dụng version cố định
         */
        fun launchGameBraDargayX(context: Context) {
            // Bypass version selection UI
            val versionId = FORCED_VERSION_ID
            
            // Chuẩn bị parameters
            val gameConfig = GameConfig(
                versionId = versionId,
                minecraftVersion = "1.21.1",
                fabricLoader = "fabric-loader-0.16.x",
                javaMaxMemory = 2048,  // 2GB
                javaMinMemory = 1024   // 1GB
            )
            
            // Gọi hàm launch gốc của PojavLauncher
            performNativeLaunch(gameConfig)
        }
        
        private fun performNativeLaunch(config: GameConfig) {
            // Gọi JNI/native code của PojavLauncher
            // Cần tìm đúng method signature
            NativeGameLauncher.startGame(config)
        }
    }
    
    data class GameConfig(
        val versionId: String,
        val minecraftVersion: String,
        val fabricLoader: String,
        val javaMaxMemory: Int,
        val javaMinMemory: Int
    )
    
    // Wrapper cho JNI
    object NativeGameLauncher {
        external fun startGame(config: GameConfig)
    }
}
```

---

## PHẦN 4: TÍCH HỢP VỚI AUTOSETUPMANAGER

Khi AutoSetupManager hoàn thành setup, tự động khởi chạy game:

```kotlin
// File: MainActivity.kt

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
                    Log.i(TAG, "Setup completed successfully")
                    showPlayButton()
                    
                    // ===== OPTIONAL: TỰ ĐỘNG KHỞI CHẠY =====
                    // Sau 1 giây, tự động khởi chạy game
                    Handler(Looper.getMainLooper()).postDelayed({
                        launchGameWithForcedVersion()
                    }, 1000)
                    
                } else {
                    Log.e(TAG, "Setup failed")
                    showSetupError()
                }
            }
        }
    )
}
```

---

## PHẦN 5: DEBUG & VERIFICATION

### 5.1 Logcat Commands

```bash
# Xem tất cả log từ app
adb logcat | grep "BraDargayX\|GameLauncher\|MainActivity"

# Filter chỉ error/warning
adb logcat | grep "E\|W" | grep "BraDargayX"

# Xem stack trace
adb logcat | grep "Exception\|Crash"
```

### 5.2 Verification Checklist

- [ ] Version Spinner ẩn hay được disable
- [ ] MainUI chỉ hiển thị Logo + Status + PLAY button
- [ ] Khi nhấn PLAY, game khởi chạy với fabric-loader-0.16.x-1.21.1
- [ ] Game có sử dụng bradargayx.jar từ assets
- [ ] JNI không crash (logs không có UnsatisfiedLinkError)

### 5.3 Command Test

```bash
# Cài APK
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Khởi chạy app
adb shell am start -n com.bradargayx.client/.ui.view.MainActivity

# Xem logs
adb logcat -c  # Clear
adb logcat | grep "bradargayx"

# Nếu crash, xem detailed error
adb logcat | grep "Exception\|AndroidRuntime"
```

---

## PHẦN 6: NẾU GẶP LỖI

| Lỗi | Nguyên Nhân | Giải Pháp |
|-----|-----------|----------|
| `UnsatisfiedLinkError` | JNI method không tìm thấy | Kiểm tra package name, không đổi sai |
| `Version not found` | Version ID sai | Kiểm tra đúng ID: "fabric-loader-0.16.x-1.21.1" |
| `Game crash ngay` | Mods không tìm thấy | Kiểm tra files trong assets/mods |
| `Spinner still showing` | Code cũ chưa xóa | Tìm và xóa version spinner từ layout cũ |

---

## PHẦN 7: FILE KHÁC CẦN KIỂM TRA

Trong PojavLauncher gốc, tìm những file này:

```bash
# Tìm tất cả files chứa version/launcher logic
grep -r "class.*Activity.*extends" app/src --include="*.kt" --include="*.java" | head -20

# Tìm files kế thừa AppCompatActivity
grep -r "extends AppCompatActivity\|extends Activity" app/src --include="*.kt" --include="*.java"

# Tìm layouts có spinner/selection
find app/src/main/res/layout -name "*.xml" -exec grep -l "Spinner\|selection" {} \;
```

Hãy xóa hoặc disable tất cả UI elements liên quan đến version selection!

---

**Kết Luận:** Phương pháp tốt nhất là:
1. Override onCreate của MainActivity
2. Ẩn/disable version selection UI
3. Khóa version thành "fabric-loader-0.16.x-1.21.1"
4. Tích hợp AutoSetupManager
5. Test trên thiết bị thực

