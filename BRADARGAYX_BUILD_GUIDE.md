# 🎮 BraDargayX Client - Hướng Dẫn Build Chi Tiết

**Tác giả:** Senior Android Developer  
**Phiên bản:** 1.0  
**Ngày:** 2026-06-01

---

## 📋 MỤC LỤC

1. [Giai đoạn 1: Gradle & Application ID](#giai-đoạn-1-gradle--application-id)
2. [Giai đoạn 2: Thiết Kế Luxury Launcher UI](#giai-đoạn-2-thiết-kế-luxury-launcher-ui)
3. [Giai đoạn 3: Bundle Assets & Tự Động Giải Nén](#giai-đoạn-3-bundle-assets--tự-động-giải-nén)
4. [Giai đoạn 4: Khóa Cấu Hình Khởi Chạy](#giai-đoạn-4-khóa-cấu-hình-khởi-chạy)
5. [Giai đoạn 5: Tối Ưu & Xuất Bản](#giai-đoạn-5-tối-ưu--xuất-bản)

---

## GIAI ĐOẠN 1: GRADLE & APPLICATION ID

### 1.1 Vấn Đề JNI (Java Native Interface) - QUAN TRỌNG ⚠️

PojavLauncher sử dụng các thư viện Native C/C++ được tham chiếu qua JNI. Khi đổi Package Name bừa bãi, các liên kết JNI sẽ bị hỏng vì:
- Các method native được ánh xạ dựa vào **Fully Qualified Class Name** 
- Nếu `net.kdt.pojavlaunch.MainActivity` đổi thành `com.bradargayx.client.MainActivity`, hệ thống JNI sẽ không tìm thấy method native.

### 1.2 Cách Thay Đổi An Toàn

**Bước 1:** Mở file `app/build.gradle`

```gradle
android {
    namespace = "com.bradargayx.client"  // ← Thay đổi này
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bradargayx.client"  // ← Và cái này
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-Release"
        
        // Cấu hình NDK cho JNI - GIỮ NGUYÊN
        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
                abiFilters("arm64-v8a", "armeabi-v7a")
            }
        }
    }
}
```

**Bước 2:** Cập nhật tất cả File Kotlin/Java References

Tìm kiếm `net.kdt.pojavlaunch` trong toàn bộ project:
```bash
grep -r "net.kdt.pojavlaunch" app/src --include="*.kt" --include="*.java"
```

Thay thế từng file một bằng cách:
- Đổi package declaration: `package com.bradargayx.client`
- Đổi import statements tương ứng
- **NHƯNG:** Giữ nguyên tất cả native method calls (các method được khai báo với `external`)

**Bước 3:** Cập Nhật AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bradargayx.client">
    
    <!-- Tất cả activity, service đều được update tự động bởi IDE -->
    <application>
        <activity
            android:name=".ui.view.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

---

### 1.3 Đổi Tên Ứng Dụng (App Name)

Mở file `app/src/main/res/values/strings.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">BraDargayX</string>
    <string name="branding_app_name">BraDargayX Client</string>
    <string name="desc_string">Fabric 1.21.1 Custom Client</string>
</resources>
```

---

### 1.4 Cách Thay Đổi App Icon (Logo)

**Bước 1:** Tạo Icon ở tất cả kích thước chuẩn:

Tải tool **Android Asset Studio** hoặc **ImageMagick**:
```bash
# Nếu dùng ImageMagick (cài: apt install imagemagick)
convert your_icon.png -resize 48x48 app/src/main/res/mipmap-mdpi/ic_launcher.png
convert your_icon.png -resize 72x72 app/src/main/res/mipmap-hdpi/ic_launcher.png
convert your_icon.png -resize 96x96 app/src/main/res/mipmap-xhdpi/ic_launcher.png
convert your_icon.png -resize 144x144 app/src/main/res/mipmap-xxhdpi/ic_launcher.png
convert your_icon.png -resize 192x192 app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
```

**Bước 2:** Cấu trúc thư mục:
```
app/src/main/res/
├── mipmap-mdpi/ic_launcher.png       (48×48)
├── mipmap-hdpi/ic_launcher.png       (72×72)
├── mipmap-xhdpi/ic_launcher.png      (96×96)
├── mipmap-xxhdpi/ic_launcher.png     (144×144)
└── mipmap-xxxhdpi/ic_launcher.png    (192×192)
```

**Bước 3:** File adaptive icon (Android 8.0+):

Tạo file `app/src/main/res/values/ic_launcher_background.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="ic_launcher_background">#121212</color>
</resources>
```

---

## GIAI ĐOẠN 2: THIẾT KẾ LUXURY LAUNCHER UI

### 2.1 Cấu Trúc Activity Chính

File: `app/src/main/res/layout/activity_main.xml`

**Mô tả:** Màn hình chỉ gồm 3 phần tử:
1. Logo BraDargayX ở giữa
2. Status Text hiển thị trạng thái (Idle, Setup..., Ready)
3. Nút PLAY dạng Capsule ở dưới

Xem file **activity_main.xml** trong folder này.

### 2.2 Shape & Drawable Resources

Cần tạo:
- `shape_gradient_button.xml` - Nút PLAY gradient
- `bg_main_dark.xml` - Background tối
- `ripple_effect.xml` - Hiệu ứng chạm

Xem folder **drawable_resources** cho chi tiết.

### 2.3 Màu Sắc & Theme

Tạo file `app/src/main/res/values/colors.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Luxury Dark Theme -->
    <color name="color_bg_dark">#121212</color>
    <color name="color_accent_purple">#B388FF</color>
    <color name="color_accent_cyan">#00E5FF</color>
    <color name="color_text_primary">#FFFFFF</color>
    <color name="color_text_secondary">#B3B3B3</color>
    
    <!-- Gradient Points -->
    <color name="gradient_start">#B388FF</color>
    <color name="gradient_end">#00E5FF</color>
</resources>
```

---

## GIAI ĐOẠN 3: BUNDLE ASSETS & TỰ ĐỘNG GIẢI NÉN

### 3.1 Cấu Trúc Assets

```
app/src/main/assets/
└── mods/
    ├── bradargayx.jar
    └── fabric-api.jar
```

**Bước 1:** Copy 2 file `.jar` vào thư mục này

### 3.2 Class AutoSetupManager.kt

File: `app/src/main/java/com/bradargayx/client/manager/AutoSetupManager.kt`

Xem file chi tiết trong folder **kotlin_source_code**.

**Chức năng chính:**
- Kiểm tra nếu lần đầu chạy app (dùng SharedPreferences)
- Copy 2 file .jar từ assets → `.minecraft/mods`
- Hiển thị ProgressBar trong quá trình copy
- Gọi callback khi hoàn thành

### 3.3 Cập Nhật MainActivity

Khi app start:

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var setupManager: AutoSetupManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupManager = AutoSetupManager(this)
        
        if (!setupManager.isSetupCompleted()) {
            // Hiển thị setup screen
            showSetupUI()
        } else {
            // Hiển thị nút PLAY
            showPlayButton()
        }
    }
}
```

---

## GIAI ĐOẠN 4: KHÓA CẤU HÌNH KHỞI CHẠY

### 4.1 Tìm Kiếm File Xử Lý Nút PLAY

Trong PojavLauncher, tìm file chứa logic khởi chạy:
```bash
grep -r "startGame\|launchGame\|onCreate" app/src --include="*.kt" --include="*.java" | grep -i "launch\|play"
```

Thường nằm ở: `app/src/main/java/net/kdt/pojavlaunch/LauncherActivity.kt` hoặc `GameLaunchActivity.kt`

### 4.2 Sửa Logic Khởi Chạy

**TRƯỚC (PojavLauncher gốc):**
```kotlin
// Người dùng chọn version từ UI
val selectedVersion = versionSpinner.selectedItem as String
launchGame(selectedVersion)
```

**SAU (BraDargayX - Force Launch):**
```kotlin
// Khóa cứng: Chỉ chạy Fabric 1.21.1
private val FORCED_VERSION = "fabric-loader-0.16.x-1.21.1"

fun onPlayButtonClick() {
    // Bỏ qua UI selection, trực tiếp dùng version cố định
    launchGame(FORCED_VERSION)
}
```

### 4.3 Cấu Hình Fabric Tự Động

File: `app/src/main/java/com/bradargayx/client/manager/GameLaunchManager.kt`

```kotlin
class GameLaunchManager(private val context: Context) {
    
    // Version cố định
    companion object {
        const val MINECRAFT_VERSION = "1.21.1"
        const val FABRIC_LOADER = "fabric-loader-0.16.x"
        const val FORCED_VERSION_ID = "fabric-loader-0.16.x-1.21.1"
    }
    
    fun launchGame() {
        // Gọi PojavLauncher's JNI launch function với FORCED_VERSION_ID
        val launchParams = GameLaunchParams(
            versionId = FORCED_VERSION_ID,
            minecraftVersion = MINECRAFT_VERSION,
            fabricLoader = FABRIC_LOADER,
            javaArgs = getOptimizedJavaArgs(),
            gameArgs = getOptimizedGameArgs()
        )
        
        performLaunch(launchParams)
    }
    
    private fun performLaunch(params: GameLaunchParams) {
        // Gọi native method (JNI) của PojavLauncher
        nativeStartGame(params)
    }
    
    external fun nativeStartGame(params: GameLaunchParams)
}
```

---

## GIAI ĐOẠN 5: TỐI ƯU & XUẤT BẢN

### 5.1 Cấu Hình ProGuard/R8

File: `app/proguard-rules.pro`

```proguard
# BraDargayX Client - ProGuard Rules

# ===== OBFUSCATION RULES =====
-optimizationpasses 5
-verbose
-dontoptimize
-dontpreverify

# Rename classes, methods, fields
-obfuscationdictionary obfuscation_dict.txt
-classobfuscationdictionary obfuscation_dict.txt
-packageobfuscationdictionary obfuscation_dict.txt

# ===== KEEP NATIVE METHODS =====
# QUAN TRỌNG: Không obfuscate native methods vì JNI cần tên chính xác
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep main activity
-keep class com.bradargayx.client.ui.view.MainActivity { *; }

# Keep callback interfaces
-keep interface com.bradargayx.client.** { *; }

# Keep model classes
-keep class com.bradargayx.client.model.** { *; }

# ===== REMOVE LOGGING =====
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ===== OPTIMIZATION =====
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
```

Tạo file `obfuscation_dict.txt` với các tên ngẫu nhiên:
```
a
b
c
fgH2
jK9p
...
```

### 5.2 Cập Nhật build.gradle cho Release

```gradle
android {
    signingConfigs {
        release {
            storeFile file("release.keystore")
            storePassword System.getenv("KEYSTORE_PASSWORD") ?: "your_password"
            keyAlias System.getenv("KEY_ALIAS") ?: "bradargayx"
            keyPassword System.getenv("KEY_PASSWORD") ?: "your_key_password"
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            
            // Bật obfuscation
            debuggable false
            
            buildConfigField "boolean", "IS_RELEASE", "true"
            
            // Tối ưu APK size
            ndk {
                debugSymbolLevel 'full'
            }
        }
    }
}
```

### 5.3 Tạo Keystore Chữ Ký Số

**Bước 1:** Tạo key-pair (Chỉ làm 1 lần):

```bash
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias bradargayx \
  -storepass your_secure_password \
  -keypass your_key_password \
  -dname "CN=BraDargayX,O=BraDargayX,C=VN"
```

**Lưu file này cẩn thận!** Nếu mất, không thể update app trên Store.

**Bước 2:** Lưu vào project:
```bash
mv release.keystore app/
```

### 5.4 Build Signed APK trong Android Studio

**Phương pháp 1: UI Build Menu**

1. Mở Android Studio
2. Menu: `Build` → `Generate Signed Bundle / APK`
3. Chọn `APK`
4. Chọn keystore: `app/release.keystore`
5. Nhập passwords
6. Chọn `release` build variant
7. Enable `V1 (JAR signature)` + `V2 (Full APK Signature)`
8. Click `Finish`

**File output:** `app/release/app-release.apk`

### 5.5 Build từ Command Line (CI/CD)

```bash
./gradlew assembleRelease \
  -PSTORE_FILE=app/release.keystore \
  -PSTORE_PASSWORD=your_password \
  -PKEY_ALIAS=bradargayx \
  -PKEY_PASSWORD=your_key_password
```

---

## ⚠️ CHECKLIST TRƯỚC KHI RELEASE

- [ ] Đổi applicationId thành `com.bradargayx.client`
- [ ] Cập nhật app name thành `BraDargayX`
- [ ] Thay icon tất cả độ phân giải
- [ ] Replace layout chính bằng Luxury UI
- [ ] Thêm assets: bradargayx.jar, fabric-api.jar
- [ ] Implement AutoSetupManager
- [ ] Force version thành fabric-loader-0.16.x-1.21.1
- [ ] ProGuard rules đầy đủ
- [ ] Tạo keystore chữ ký
- [ ] Test trên thiết bị thực (không chỉ emulator)
- [ ] Kiểm tra JNI hoạt động (Game có launch được không)

---

## 📞 TROUBLESHOOTING

### JNI Crash
```
java.lang.UnsatisfiedLinkError: ...
```
→ Kiểm tra package name có thay đổi đúng cách không. Gradle rebuild clean.

### Setup không hoàn thành
→ Kiểm tra quyền write file. Đảm bảo context được pass đúng vào AutoSetupManager.

### APK quá lớn
→ Enable shrinkResources. Loại bỏ drawable, layout không dùng.

---

**Nếu gặp vấn đề, hãy liên hệ hoặc kiểm tra logs:**
```bash
adb logcat | grep -i "bradargayx\|error\|crash"
```

