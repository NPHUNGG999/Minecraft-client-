# 📂 Cấu Trúc Thư Mục BraDargayX Project

Sau khi tích hợp tất cả các file, project của bạn sẽ có cấu trúc như sau:

```
BraDargayX-Client/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/bradargayx/client/
│   │   │   │   ├── ui/view/
│   │   │   │   │   └── MainActivity.kt                   [ĐƯỢC CẤP]
│   │   │   │   ├── manager/
│   │   │   │   │   ├── AutoSetupManager.kt               [ĐƯỢC CẤP]
│   │   │   │   │   └── GameLaunchManager.kt              [ĐƯỢC CẤP]
│   │   │   │   └── model/
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── animator/
│   │   │   │   │   └── btn_state_animator.xml            [ĐƯỢC CẤP]
│   │   │   │   │
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── shape_gradient_button.xml         [ĐƯỢC CẤP]
│   │   │   │   │   ├── ripple_effect.xml                 [ĐƯỢC CẤP]
│   │   │   │   │   ├── ic_launcher_background.xml        [ĐƯỢC CẤP]
│   │   │   │   │
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml                 [ĐƯỢC CẤP]
│   │   │   │   │
│   │   │   │   ├── mipmap-*/
│   │   │   │   │   ├── ic_launcher.png                   [CẦN THÊM TỪ BẠN]
│   │   │   │   │   └── ic_launcher_round.png             [CẦN THÊM TỪ BẠN]
│   │   │   │   │
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml                        [ĐƯỢC CẤP]
│   │   │   │   │   ├── strings.xml                       [ĐƯỢC CẤP]
│   │   │   │   │   ├── styles.xml                        [ĐƯỢC CẤP]
│   │   │   │   │   └── themes.xml                        [TỰY CHỌN]
│   │   │   │   │
│   │   │   │   ├── values-night/
│   │   │   │   │   └── colors.xml                        [TỰY CHỌN - Dark theme override]
│   │   │   │   │
│   │   │   │   ├── font/
│   │   │   │   │   ├── montserrat_regular.ttf            [CẦN THÊM]
│   │   │   │   │   └── montserrat_bold.ttf               [CẦN THÊM]
│   │   │   │   │
│   │   │   │   └── xml/
│   │   │   │       ├── data_extraction_rules.xml         [ĐƯỢC CẤP]
│   │   │   │       └── backup_descriptor.xml             [ĐƯỢC CẤP]
│   │   │   │
│   │   │   ├── assets/
│   │   │   │   └── mods/
│   │   │   │       ├── bradargayx.jar                    [CẦN THÊM - Your client]
│   │   │   │       └── fabric-api.jar                    [CẦN THÊM - Fabric dependency]
│   │   │   │
│   │   │   └── AndroidManifest.xml                        [ĐƯỢC CẤP]
│   │   │
│   │   ├── test/                                          [Existing from template]
│   │   └── androidTest/                                   [Existing from template]
│   │
│   ├── proguard-rules.pro                                 [ĐƯỢC CẤP]
│   ├── obfuscation_dict.txt                               [ĐƯỢC CẤP]
│   └── build.gradle                                       [ĐƯỢC CẤP - MODIFIED]
│
├── gradle/
│   ├── wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties                      [Đảm bảo Gradle 8.0+]
│
├── build.gradle                                           [Root - không thay đổi]
├── settings.gradle                                        [Root - chứa module references]
├── local.properties                                       [Auto-generated, không commit]
├── gradle.properties                                      [Gradle config]
│
├── release.keystore                                       [ĐƯỢC TẠO - Bạn cần tạo này]
│
└── README.md                                              [Hướng dẫn project]
```

---

## 📋 CHI CHỈ TỪNG FILE CẦN TẠO

### ✅ FILES ĐÃ ĐƯỢC CẤP (Bạn tìm trong workspace này)

1. **BRADARGAYX_BUILD_GUIDE.md** - Hướng dẫn build chính
2. **LAYOUT_activity_main.xml** - Giao diện Luxury
3. **DRAWABLE_shape_gradient_button.xml** - Shape gradient nút
4. **DRAWABLE_ripple_effect.xml** - Ripple effect
5. **ANIMATOR_btn_state_animator.xml** - Button animation
6. **VALUES_strings.xml** - Text resources
7. **VALUES_colors.xml** - Color palette
8. **VALUES_styles.xml** - Theme styles
9. **KOTLIN_AutoSetupManager.kt** - Setup manager
10. **KOTLIN_GameLaunchManager.kt** - Launch manager
11. **KOTLIN_MainActivity.kt** - Main activity
12. **BUILD_app_build.gradle** - Build config
13. **PROGUARD_proguard_rules.pro** - Obfuscation rules
14. **OBFUSCATION_obfuscation_dict.txt** - Obfuscation dictionary
15. **MANIFEST_AndroidManifest.xml** - Android manifest

---

### ⚠️ FILES CẦN THÊM TỪMŨ BẠN

#### 1. **App Icons** (Đặt vào `app/src/main/res/mipmap-*/`)

**Quy trình chuẩn:**
```bash
# Tạo icon 512x512 (Source)
# Dùng Android Asset Studio hoặc ImageMagick:

convert your_512.png -resize 48x48 app/src/main/res/mipmap-mdpi/ic_launcher.png
convert your_512.png -resize 72x72 app/src/main/res/mipmap-hdpi/ic_launcher.png
convert your_512.png -resize 96x96 app/src/main/res/mipmap-xhdpi/ic_launcher.png
convert your_512.png -resize 144x144 app/src/main/res/mipmap-xxhdpi/ic_launcher.png
convert your_512.png -resize 192x192 app/src/main/res/mipmap-xxxhdpi/ic_launcher.png

# Tạo adaptive icon (Android 8.0+)
# Tương tự cho ic_launcher_round.png
```

#### 2. **Fonts** (Đặt vào `app/src/main/res/font/`)

- `montserrat_regular.ttf` - Font regular từ Google Fonts
- `montserrat_bold.ttf` - Font bold từ Google Fonts

[Tải từ](https://fonts.google.com/specimen/Montserrat)

#### 3. **Mod Files** (Đặt vào `app/src/main/assets/mods/`)

- `bradargayx.jar` - Your custom client JAR
- `fabric-api.jar` - Fabric API compatibility

Đảm bảo tên file CHÍNH XÁC, không có typo!

#### 4. **Mod Files Details**

```
app/src/main/assets/mods/
├── bradargayx.jar
│   └── Compiled bytecode của BraDargayX client
│   └── Phải compatible với Fabric 1.21.1
│
└── fabric-api.jar
    └── Download từ: https://modrinth.com/mod/fabric-api
    └── Version: 1.21.1
```

---

## 🔧 CÁC BƯỚC TỰC THY SETUP

### Bước 1: Clone PojavLauncher Fork

```bash
git clone <your_pojavlauncher_fork> BraDargayX-Client
cd BraDargayX-Client
```

### Bước 2: Sử Dụng Files Được Cấp

Trong folder workspace hiện tại, bạn sẽ tìm thấy 15 file có prefix như:
- `KOTLIN_*.kt`
- `LAYOUT_*.xml`
- `DRAWABLE_*.xml`
- `VALUES_*.xml`
- `BUILD_*.gradle`
- `PROGUARD_*.pro`
- `MANIFEST_*.xml`

**Sao chép chúng đến project:**

```bash
# Copy Kotlin files
cp KOTLIN_MainActivity.kt app/src/main/java/com/bradargayx/client/ui/view/
cp KOTLIN_AutoSetupManager.kt app/src/main/java/com/bradargayx/client/manager/
cp KOTLIN_GameLaunchManager.kt app/src/main/java/com/bradargayx/client/manager/

# Copy Layout
cp LAYOUT_activity_main.xml app/src/main/res/layout/activity_main.xml

# Copy Drawables
cp DRAWABLE_*.xml app/src/main/res/drawable/

# Copy Values
cp VALUES_*.xml app/src/main/res/values/

# Copy Build Config
cp BUILD_app_build.gradle app/build.gradle
cp PROGUARD_*.pro app/
cp OBFUSCATION_*.txt app/

# Copy Manifest
cp MANIFEST_AndroidManifest.xml app/src/main/AndroidManifest.xml
```

### Bước 3: Tạo Thư Mục Cần Thiết

```bash
mkdir -p app/src/main/java/com/bradargayx/client/{ui/view,manager,model}
mkdir -p app/src/main/res/{layout,drawable,values,values-night,font,xml,animator}
mkdir -p app/src/main/res/mipmap-{mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi}
mkdir -p app/src/main/assets/mods
```

### Bước 4: Thêm Icons & Fonts

```bash
# Thêm icons
# (Quy trình ở phần trên)

# Thêm fonts
# Download Montserrat từ Google Fonts
cp montserrat_*.ttf app/src/main/res/font/
```

### Bước 5: Thêm Mod Files

```bash
cp /path/to/bradargayx.jar app/src/main/assets/mods/
cp /path/to/fabric-api.jar app/src/main/assets/mods/
```

### Bước 6: Cấu Hình Gradle

Mở `app/build.gradle`, kiểm tra:
```gradle
applicationId = "com.bradargayx.client"
namespace = "com.bradargayx.client"
minSdk = 26
targetSdk = 34
```

### Bước 7: Tạo Keystore

```bash
keytool -genkey -v -keystore app/release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias bradargayx \
  -storepass MySecurePassword123! \
  -keypass MyKeyPassword456! \
  -dname "CN=BraDargayX,O=BraDargayX,C=VN"
```

**LƯU FILE KEYSTORE CẢN THẬN!** Nếu mất, bạn không thể update app.

### Bước 8: Sync & Build

Android Studio:
```
Build → Clean Project
Build → Rebuild Project
Build → Analyze & Run
```

Hoặc command line:
```bash
./gradlew clean
./gradlew assembleDebug
./gradlew assembleRelease
```

---

## 🚀 OUTPUT FILES

### Sau khi build xong:

**Debug APK:**
```
app/build/outputs/apk/debug/app-debug.apk
```

**Release APK (có chữ ký số):**
```
app/build/outputs/apk/release/app-release.apk
```

**Kiểm tra APK:**
```bash
# Cài lên thiết bị
adb install -r app/build/outputs/apk/release/app-release.apk

# Xem log
adb logcat | grep -i "bradargayx"
```

---

## ⚙️ CẤU HÌNH GradlePropertiesGradleProperties

File `gradle.properties` (root):
```properties
# Project configuration
android.useAndroidX=true
android.enableJetifier=true

# Optimization
android.enableR8=true
android.enableR8.fullMode=false

# Build performance
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.jvmargs=-Xmx4096m

# Gradle daemon
org.gradle.daemon=true
```

---

## 📊 TROUBLESHOOTING

| Lỗi | Nguyên Nhân | Giải Pháp |
|-----|------------|----------|
| `JNI crash` | Package name thay đổi sai | Kiểm tra namespace & applicationId |
| `Setup không copy file` | File không tồn tại trong assets | Kiểm tra tên file và đường dẫn |
| `APK quá lớn` | ProGuard không hoạt động | Enable shrinkResources & minifyEnabled |
| `Build fail: Cannot find NDK` | NDK không install | Cài qua SDK Manager |

---

**Chúc bạn thành công! 🎮**

Nếu có vấn đề, kiểm tra:
1. Logs: `adb logcat`
2. Android Studio Build Output
3. Đảm bảo tất cả file được copy đúng chỗ
