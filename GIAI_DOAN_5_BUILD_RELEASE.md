# 📦 GIAI ĐOẠN 5 CHI TIẾT: TỐI ƯU & XUẤT BẢN RELEASE APK

## PHẦN 1: CẤU HÌNH R8 (ProGuard Mới)

### 1.1 Hiểu R8 là gì?

- **R8** là công cụ code shrinking, obfuscation, optimization của Google
- Thay thế ProGuard (cũ) trong Android Studio 3.4+
- Giúp:
  - Giảm APK size (loại bỏ code không dùng)
  - Obfuscate code (làm khó reverse-engineer)
  - Optimize performance

### 1.2 Cấu Hình Trong build.gradle

```gradle
android {
    buildTypes {
        release {
            minifyEnabled = true              // Bật R8 shrinking
            shrinkResources = true            // Xóa unused resources
            proguardFiles(
                getDefaultProguardFile('proguard-android-optimize.txt'),
                'proguard-rules.pro'          // Tệp quy tắc custom
            )
        }
    }
}

// Gradle properties (gradle.properties)
android.enableR8=true                        // Bật R8
android.enableR8.fullMode=false              // Cải thiện compatibility
```

### 1.3 File ProGuard-Rules.pro Explained

Mọi dòng trong `proguard-rules.pro`:

```proguard
# ===== OPTIMIZATION PASSES =====
-optimizationpasses 5
# → Chạy 5 lần optimization, càng nhiều càng tốt (nhưng build lâu)

-verbose
# → In chi tiết vào console

-dontoptimize
# → Tắt optimization (giữ logic không thay đổi)
# ← CHỈ dùng khi gặp issue

# ===== KEEP NATIVE METHODS (CRITICAL!) =====
-keepclasseswithmembernames class * {
    native <methods>;
}
# → Giữ nguyên tên của tất cả method native
# → JNI cần tên chính xác!

# ===== KEEP MAIN ACTIVITY =====
-keep class com.bradargayx.client.ui.view.MainActivity { *; }
# → Không obfuscate tên class MainActivity
# → Android cần tìm thấy nó từ AndroidManifest

# ===== REMOVE LOGGING =====
-assumenosideeffects class android.util.Log {
    public static *** d(...);  # Log.d()
    public static *** v(...);  # Log.v()
    public static *** i(...);  # Log.i()
}
# → Xóa tất cả logging calls (giảm APK size)
# → Nhưng giữ lại Log.e() và Log.w() để debug

# ===== OPTIMIZATION RULES =====
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# → Fine-tune optimization (tránh issue)
```

---

## PHẦN 2: TẠO KEYSTORE (CHỮ KÝ SỐ)

### 2.1 Tại Sao Cần Keystore?

- **Keystore** = Private key + Certificate
- Dùng để ký APK (chứng minh bạn là tác giả)
- Google Play & các app store yêu cầu
- **KHÔNG thể thay đổi sau khi upload** - lưu trữ cẩn thận!

### 2.2 Tạo Keystore (Chỉ làm 1 lần)

```bash
# Command tạo keystore
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias bradargayx \
  -storepass MySecurePassword123! \
  -keypass MyKeyPassword456! \
  -dname "CN=BraDargayX Developer,O=BraDargayX,C=VN"

# Giải thích:
# -genkey                    = Tạo key mới
# -v                         = Verbose (in chi tiết)
# -keystore release.keystore = Tên file keystore
# -keyalg RSA                = Thuật toán mã hóa (RSA tốt nhất)
# -keysize 2048              = Độ dài khóa (2048 bit safe)
# -validity 10000            = Hiệu lực 10000 ngày (~27 năm)
# -alias bradargayx          = Nickname của key
# -storepass                 = Password của keystore (GIỮ BÍ MẬT!)
# -keypass                   = Password của key (GIỮ BÍ MẬT!)
# -dname                     = Thông tin cá nhân (CN=Common Name)
```

### 2.3 Lưu Trữ Keystore An Toàn

```bash
# Bước 1: Đặt keystore vào app folder
mv release.keystore app/

# Bước 2: Thêm vào .gitignore (ĐỪNG commit keystore!)
echo "release.keystore" >> .gitignore
echo "*.keystore" >> .gitignore

# Bước 3: Backup keystore ở nơi an toàn
# - USB drive được mã hóa
# - Cloud storage riêng tư (Google Drive, Dropbox)
# - KHÔNG nên share công khai!

# Bước 4: Lưu password ở nơi an toàn
# - Password manager (1Password, Bitwarden)
# - NOT in plain text file!
```

### 2.4 Kiểm Tra Keystore

```bash
# Xem thông tin keystore
keytool -list -v -keystore app/release.keystore -storepass MySecurePassword123!

# Output:
# Keystore type: PKCS12
# Keystore provider: SUN
# Your keystore contains 1 entry
# Alias name: bradargayx
# Owner: CN=BraDargayX Developer, O=BraDargayX, C=VN
# ...
# Valid from: Wed Jun 01 00:00:00 UTC 2026 until Wed May 18 00:00:00 UTC 2053
```

---

## PHẦN 3: BUILD RELEASE APK TRÊN ANDROID STUDIO

### 3.1 Phương Pháp 1: Sử Dụng UI Menu (Dễ nhất)

**Bước 1:** Mở Android Studio, project BraDargayX
```
File → Project Structure
```

**Bước 2:** Vào signing config
```
Modules → app → Signing Configs
```

**Bước 3:** Click "+" thêm signing config
```
Name: release
Store file: app/release.keystore
Store password: MySecurePassword123!
Key alias: bradargayx
Key password: MyKeyPassword456!
```

**Bước 4:** Build menu
```
Build → Generate Signed Bundle / APK
```

**Bước 5:** Chọn APK (không phải Bundle)
```
( ) Android App Bundle  ← Chỉ cho Google Play
(✓) APK                 ← Cho tất cả device
```

**Bước 6:** Chọn keystore
```
Keystore path: app/release.keystore
Password: MySecurePassword123!
Key alias: bradargayx
Key password: MyKeyPassword456!
```

**Bước 7:** Chọn build variant
```
( ) debug
(✓) release  ← CHỌN CÁI NÀY
```

**Bước 8:** Chọn signature versions
```
☑ V1 (JAR signature)
☑ V2 (Full APK Signature)
```

Cả 2 cách đều enable để tương thích tốt nhất.

**Bước 9:** Click "Finish"

**Output:** `app/release/app-release.apk`

### 3.2 Phương Pháp 2: Từ Command Line

```bash
# Step 1: Clean & build
./gradlew clean

# Step 2: Build release
./gradlew assembleRelease \
  -PSTORE_FILE=app/release.keystore \
  -PSTORE_PASSWORD="MySecurePassword123!" \
  -PKEY_ALIAS="bradargayx" \
  -PKEY_PASSWORD="MyKeyPassword456!"

# Output: app/build/outputs/apk/release/app-release.apk
```

**Cấu hình trong build.gradle để nhận params:**

```gradle
android {
    signingConfigs {
        release {
            storeFile file(project.hasProperty('STORE_FILE') ? project.STORE_FILE : "app/release.keystore")
            storePassword project.hasProperty('STORE_PASSWORD') ? project.STORE_PASSWORD : ""
            keyAlias project.hasProperty('KEY_ALIAS') ? project.KEY_ALIAS : "bradargayx"
            keyPassword project.hasProperty('KEY_PASSWORD') ? project.KEY_PASSWORD : ""
        }
    }
}
```

### 3.3 Phương Pháp 3: Sử Dụng Biến Môi Trường (Safer)

```bash
# Thay vì hardcode password, sử dụng env vars:

export KEYSTORE_PASSWORD="MySecurePassword123!"
export KEY_ALIAS="bradargayx"
export KEY_PASSWORD="MyKeyPassword456!"

./gradlew assembleRelease
```

**Cấu hình build.gradle:**

```gradle
android {
    signingConfigs {
        release {
            storeFile file("app/release.keystore")
            storePassword System.getenv("KEYSTORE_PASSWORD") ?: ""
            keyAlias System.getenv("KEY_ALIAS") ?: ""
            keyPassword System.getenv("KEY_PASSWORD") ?: ""
        }
    }
}
```

---

## PHẦN 4: KIỂM TRA & VERIFY APK

### 4.1 Kiểm Tra Cơ Bản

```bash
# File size
ls -lh app/release/app-release.apk
# Expected: ~30-50 MB (tùy PojavLauncher size)

# Signature info
jarsigner -verify -verbose app/release/app-release.apk
# Output: jar verified

# APK info
aapt dump badging app/release/app-release.apk
# Xem app name, package, permissions
```

### 4.2 Cài & Test Trên Thiết Bị

```bash
# Cài APK
adb install -r app/release/app-release.apk

# Khởi chạy app
adb shell am start -n com.bradargayx.client/.ui.view.MainActivity

# Xem logs (kiểm tra crash)
adb logcat -c
adb logcat | grep -i "bradargayx\|error\|exception"

# Test setup process
# 1. Chờ setup hoàn tất
# 2. Nhấn PLAY button
# 3. Game có khởi chạy không?
```

### 4.3 Decompile & Verify Obfuscation

```bash
# Dùng APKTool hoặc jadx để kiểm tra
apktool d app/release/app-release.apk -o bradargayx_extracted

# Kiểm tra code có bị obfuscate không
grep -r "class a\|class b\|class c" bradargayx_extracted/smali/
# Nếu thấy "a, b, c..." = obfuscation hoạt động ✓

# Kiểm tra native method vẫn tồn tại
grep -r "native " bradargayx_extracted/smali/ | grep -i "game\|launch"
# Nếu vẫn thấy tên method gốc = GOOD ✓
```

---

## PHẦN 5: TỐI ƯU APK SIZE

### 5.1 Kiểm Tra What's In My APK

Android Studio built-in:
```
Build → Analyze APK
→ Chọn app-release.apk
→ Xem breakdown:
   - Resources: ~40%
   - Native libs: ~50%
   - Dex files: ~10%
```

### 5.2 Giảm Size

```gradle
android {
    buildTypes {
        release {
            shrinkResources = true     // ← Loại bỏ unused resources
            minifyEnabled = true       // ← Loại bỏ unused code
            
            // Nén resources
            android {
                packagingOptions {
                    exclude 'META-INF/proguard/androidx*.pro'
                    exclude 'META-INF/androidx*.version'
                }
            }
        }
    }
    
    // Chỉ build cho architectures cần thiết
    splits {
        abi {
            enable = true
            reset()
            include 'arm64-v8a', 'armeabi-v7a'  // Không cần x86, x86_64
            universalApk = false  // Tạo 2 APK riêng biệt
        }
    }
}
```

### 5.3 Size Optimization Tips

| Tactic | Effect | Difficulty |
|--------|--------|------------|
| shrinkResources | -5-10% | Easy |
| minifyEnabled | -10-20% | Easy |
| Remove unused dependencies | -5-15% | Medium |
| Split APK by ABI | -30-40% | Medium |
| Compress native libs | -5-10% | Medium |

---

## PHẦN 6: UPLOAD & DISTRIBUTE

### 6.1 Local Installation

```bash
# Install on emulator
adb -e install app/release/app-release.apk

# Install on device
adb -d install app/release/app-release.apk

# Reinstall (force replace)
adb install -r app/release/app-release.apk

# Uninstall
adb uninstall com.bradargayx.client
```

### 6.2 Share APK File

```bash
# Copy to shared location
cp app/release/app-release.apk ~/Downloads/BraDargayX-1.0.0-Release.apk

# Create file listing
ls -lh ~/Downloads/BraDargayX*.apk

# Calculate checksum (untuk verify)
sha256sum ~/Downloads/BraDargayX-1.0.0-Release.apk
# Output: abc123... (share this để user verify)
```

### 6.3 Upload to Google Play Store (Optional)

1. Tạo Google Play Developer Account ($25 one-time)
2. Tạo app listing
3. Upload app-release.apk
4. Google Play sẽ re-sign nó (không dùng keystore của bạn)

⚠️ **Quan trọng:** Nếu upload lên Play Store, KHÔNG THAY ĐỔI KEYSTORE - sẽ không thể update!

---

## PHẦN 7: TROUBLESHOOTING BUILD

| Error | Cause | Solution |
|-------|-------|----------|
| `Keystore file not found` | Đường dẫn sai | Kiểm tra `app/release.keystore` tồn tại |
| `Invalid keystore password` | Password sai | Double-check password |
| `Build fails: NDK not found` | NDK chưa install | SDK Manager → Install NDK |
| `APK too large` | Code/resources chưa optimize | Enable shrinkResources & minify |
| `Native method crash` | Obfuscation làm hỏng JNI | Kiểm tra proguard-rules.pro |
| `Permission denied (keystore)` | File permission issue | `chmod 600 app/release.keystore` |

---

## PHẦN 8: FINAL CHECKLIST

Trước khi release:

- [ ] Tested on 2+ real devices
- [ ] Game successfully launches with fabric-loader-0.16.x-1.21.1
- [ ] APK size < 100 MB
- [ ] All permissions granted correctly
- [ ] No crashes in logcat
- [ ] Setup process works smoothly
- [ ] Obfuscation enabled (R8)
- [ ] Keystore backed up safely
- [ ] Version code increased (versionCode = 1, 2, 3...)
- [ ] Version name updated (versionName = "1.0.0")

---

## PHẦN 9: BUILD AUTOMATION (CI/CD)

### Nếu muốn automate build process:

```bash
#!/bin/bash
# build_release.sh

set -e

echo "=== BraDargayX Build Script ==="

# Load env vars
source .env  # Contains KEYSTORE_PASSWORD, KEY_PASSWORD

# Clean
./gradlew clean

# Build release
./gradlew assembleRelease \
  -PSTORE_FILE=app/release.keystore \
  -PSTORE_PASSWORD="$KEYSTORE_PASSWORD" \
  -PKEY_ALIAS="bradargayx" \
  -PKEY_PASSWORD="$KEY_PASSWORD"

# Check result
if [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
    echo "✓ Build successful!"
    ls -lh app/build/outputs/apk/release/app-release.apk
else
    echo "✗ Build failed!"
    exit 1
fi
```

---

**Chúc mừng! Bạn có app-release.apk sẵn sàng distribution! 🎉**

