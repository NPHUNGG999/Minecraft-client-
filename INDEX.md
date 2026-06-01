# 📖 BraDargayX Client - Complete Documentation Index

## 🎯 START HERE!

Bạn sắp xây dựng ứng dụng **BraDargayX Client** - một ứng dụng Launcher sang trọng cho Minecraft mod Fabric 1.21.1.

**Workspace này chứa 21 files**: 4 guides + 10 XML/Kotlin code + 4 build config + 3 value files.

---

## 📚 HƯỚNG DẪN THEO THỨ TỰ ĐỌC

### 🔴 BƯỚC 1: ĐỌC HƯỚNG DẪN CHÍNH
**File:** [BRADARGAYX_BUILD_GUIDE.md](BRADARGAYX_BUILD_GUIDE.md)
- **Thời gian:** 20-30 phút
- **Nội dung:** Overview 5 giai đoạn, warnings quan trọng
- **Kết thúc:** Bạn hiểu được architecture tổng quát

### 🟠 BƯỚC 2: SETUP CẤU TRÚC DỰ ÁN
**File:** [SETUP_DIRECTORY_STRUCTURE.md](SETUP_DIRECTORY_STRUCTURE.md)
- **Thời gian:** 10-15 phút (+ 20 phút execute)
- **Nội dung:** Cách sắp xếp files, lệnh copy, bước-by-bước
- **Kết thúc:** Project structure sẵn sàng

### 🟡 BƯỚC 3: GIAI ĐOẠN 4 - LOCK KHỞI CHẠY
**File:** [GIAI_DOAN_4_FORCE_LAUNCH.md](GIAI_DOAN_4_FORCE_LAUNCH.md)
- **Thời gian:** 15-20 phút
- **Nội dung:** Cách modify PojavLauncher force version
- **Kết thúc:** Hiểu cách lock version thành fabric-1.21.1

### 🟢 BƯỚC 4: GIAI ĐOẠN 5 - BUILD & RELEASE
**File:** [GIAI_DOAN_5_BUILD_RELEASE.md](GIAI_DOAN_5_BUILD_RELEASE.md)
- **Thời gian:** 20-30 phút (+ build time)
- **Nội dung:** Keystore, R8 obfuscation, build release APK
- **Kết thúc:** Có file app-release.apk sẵn sàng

### 📋 BƯỚC 5: CHECK DANH SÁCH FILES
**File:** [FILES_COMPLETE_CHECKLIST.md](FILES_COMPLETE_CHECKLIST.md)
- **Thời gian:** 5 phút
- **Nội dung:** Danh sách 21 files, nơi đặt chúng
- **Kết thúc:** Biết file nào đã có, file nào cần thêm

---

## 🎯 NAVIGATE BY TOPIC

### 🎨 Giao Diện & Design
| File | Nội dung | Hành động |
|------|---------|----------|
| [LAYOUT_activity_main.xml](LAYOUT_activity_main.xml) | Luxury Modern UI | Copy → `res/layout/` |
| [DRAWABLE_shape_gradient_button.xml](DRAWABLE_shape_gradient_button.xml) | Gradient button | Copy → `res/drawable/` |
| [DRAWABLE_ripple_effect.xml](DRAWABLE_ripple_effect.xml) | Ripple effect | Copy → `res/drawable/` |
| [ANIMATOR_btn_state_animator.xml](ANIMATOR_btn_state_animator.xml) | Button animation | Copy → `res/animator/` |
| [VALUES_colors.xml](VALUES_colors.xml) | Color palette | Copy → `res/values/` |
| [VALUES_styles.xml](VALUES_styles.xml) | Theme & styles | Copy → `res/values/` |

### 💻 Lập Trình (Kotlin)
| File | Lớp | Mục đích |
|------|-----|---------|
| [KOTLIN_MainActivity.kt](KOTLIN_MainActivity.kt) | `MainActivity` | UI chính + setup flow |
| [KOTLIN_AutoSetupManager.kt](KOTLIN_AutoSetupManager.kt) | `AutoSetupManager` | Copy mods từ assets |
| [KOTLIN_GameLaunchManager.kt](KOTLIN_GameLaunchManager.kt) | `GameLaunchManager` | Force launch + JNI wrapper |

### ⚙️ Build & Release
| File | Loại | Mục đích |
|------|------|---------|
| [BUILD_app_build.gradle](BUILD_app_build.gradle) | Gradle | Build config, dependencies |
| [PROGUARD_proguard_rules.pro](PROGUARD_proguard_rules.pro) | ProGuard | Obfuscation rules |
| [OBFUSCATION_obfuscation_dict.txt](OBFUSCATION_obfuscation_dict.txt) | Dictionary | Obfuscation names |
| [MANIFEST_AndroidManifest.xml](MANIFEST_AndroidManifest.xml) | Manifest | App permissions, activities |

### 📄 Resources
| File | Loại | Mục đích |
|------|------|---------|
| [VALUES_strings.xml](VALUES_strings.xml) | Strings | App name, text resources |
| [XML_data_extraction_rules.xml](XML_data_extraction_rules.xml) | Backup rules | Android 12+ data backup |
| [XML_backup_descriptor.xml](XML_backup_descriptor.xml) | Backup rules | Android 11- data backup |

---

## 🔍 FIND FILES BY PROBLEM

### ❌ Lỗi JNI - Native Method Crash
**Nguyên nhân:** Package name đổi sai
**Kiểm tra:** 
1. [BRADARGAYX_BUILD_GUIDE.md#giai-đoạn-1](BRADARGAYX_BUILD_GUIDE.md) - Section 1.1 & 1.2
2. [BUILD_app_build.gradle](BUILD_app_build.gradle) - applicationId & namespace

### ❌ Setup Không Hoạt Động
**Nguyên nhân:** Assets/setup manager có issue
**Kiểm tra:**
1. [KOTLIN_AutoSetupManager.kt](KOTLIN_AutoSetupManager.kt) - Check file paths
2. [SETUP_DIRECTORY_STRUCTURE.md](SETUP_DIRECTORY_STRUCTURE.md) - assets/mods folder
3. Test: `adb shell ls -la /data/data/com.bradargayx.client/.minecraft/mods`

### ❌ UI Không Đẹp
**Nguyên nhân:** Layout hoặc colors sai
**Kiểm tra:**
1. [LAYOUT_activity_main.xml](LAYOUT_activity_main.xml) - Check layout structure
2. [VALUES_colors.xml](VALUES_colors.xml) - Check color codes
3. [DRAWABLE_shape_gradient_button.xml](DRAWABLE_shape_gradient_button.xml) - Check gradient

### ❌ Build Fail - R8/ProGuard
**Nguyên nhân:** ProGuard rules không đúng
**Kiểm tra:**
1. [PROGUARD_proguard_rules.pro](PROGUARD_proguard_rules.pro) - Keep native methods!
2. [GIAI_DOAN_5_BUILD_RELEASE.md#phần-1](GIAI_DOAN_5_BUILD_RELEASE.md) - R8 config

### ❌ APK Quá Lớn
**Nguyên nhân:** Chưa optimize
**Giải pháp:**
1. [BUILD_app_build.gradle](BUILD_app_build.gradle) - Enable shrinkResources
2. [GIAI_DOAN_5_BUILD_RELEASE.md#phần-5](GIAI_DOAN_5_BUILD_RELEASE.md) - Size optimization

---

## 📋 QUICK COMMANDS

### Copy all XML files
```bash
cp DRAWABLE_*.xml app/src/main/res/drawable/
cp LAYOUT_*.xml app/src/main/res/layout/activity_main.xml
cp ANIMATOR_*.xml app/src/main/res/animator/
cp VALUES_*.xml app/src/main/res/values/
cp XML_*.xml app/src/main/res/xml/
```

### Copy all Kotlin files
```bash
cp KOTLIN_MainActivity.kt app/src/main/java/com/bradargayx/client/ui/view/
cp KOTLIN_AutoSetupManager.kt app/src/main/java/com/bradargayx/client/manager/
cp KOTLIN_GameLaunchManager.kt app/src/main/java/com/bradargayx/client/manager/
```

### Copy build config
```bash
cp BUILD_app_build.gradle app/build.gradle
cp PROGUARD_*.pro app/
cp OBFUSCATION_*.txt app/
cp MANIFEST_AndroidManifest.xml app/src/main/AndroidManifest.xml
```

### Create directories
```bash
mkdir -p app/src/main/java/com/bradargayx/client/{ui/view,manager,model}
mkdir -p app/src/main/res/{drawable,layout,values,animator,xml}
mkdir -p app/src/main/assets/mods
```

---

## 🎯 5 GIAI ĐOẠN TÓM TẮT

### GIAI ĐOẠN 1: Gradle & Application ID
**Files:** [BUILD_app_build.gradle](BUILD_app_build.gradle) + [MANIFEST_AndroidManifest.xml](MANIFEST_AndroidManifest.xml)
- applicationId: `com.bradargayx.client`
- Tạo icons ở tất cả độ phân giải
- Giữ nguyên NDK (JNI)

### GIAI ĐOẠN 2: Luxury Launcher UI
**Files:** [LAYOUT_activity_main.xml](LAYOUT_activity_main.xml) + [DRAWABLE_*.xml](DRAWABLE_shape_gradient_button.xml) + [VALUES_*.xml](VALUES_colors.xml)
- Dark theme #121212
- Gradient button: Tím → Cyan
- Logo + Status + PLAY button

### GIAI ĐOẠN 3: Bundle Assets & Auto Setup
**Files:** [KOTLIN_AutoSetupManager.kt](KOTLIN_AutoSetupManager.kt) + [KOTLIN_MainActivity.kt](KOTLIN_MainActivity.kt)
- Copy bradargayx.jar & fabric-api.jar từ assets
- SharedPreferences tracking
- Progress bar UI

### GIAI ĐOẠN 4: Force Launch Logic
**Files:** [KOTLIN_GameLaunchManager.kt](KOTLIN_GameLaunchManager.kt) + [GIAI_DOAN_4_FORCE_LAUNCH.md](GIAI_DOAN_4_FORCE_LAUNCH.md)
- Khóa version: "fabric-loader-0.16.x-1.21.1"
- Bỏ qua version spinner
- JNI integration

### GIAI ĐOẠN 5: Optimize & Build
**Files:** [PROGUARD_proguard_rules.pro](PROGUARD_proguard_rules.pro) + [GIAI_DOAN_5_BUILD_RELEASE.md](GIAI_DOAN_5_BUILD_RELEASE.md)
- R8 obfuscation
- Keystore generation
- Release APK build

---

## 📊 FILE STATISTICS

| Loại | Số lượng | Dung lượng |
|------|---------|----------|
| Guides | 4 | ~100 KB |
| XML Files | 7 | ~25 KB |
| Kotlin | 3 | ~30 KB |
| Build Config | 4 | ~40 KB |
| Values | 3 | ~15 KB |
| **TOTAL** | **21** | **~210 KB** |

---

## ✅ PRE-FLIGHT CHECKLIST

Trước khi bắt đầu:

- [ ] Bạn có PojavLauncher fork không?
- [ ] Android Studio 2021.1+ installed?
- [ ] JDK 11+ installed?
- [ ] NDK installed (via SDK Manager)?
- [ ] Bạn có bradargayx.jar không?
- [ ] Bạn có fabric-api.jar không?
- [ ] Bạn có icon 512×512 không?

---

## 🎓 LEARNING PATH

### Nếu bạn mới bắt đầu Android:
1. Đọc [BRADARGAYX_BUILD_GUIDE.md](BRADARGAYX_BUILD_GUIDE.md) - Overview
2. Xem [LAYOUT_activity_main.xml](LAYOUT_activity_main.xml) - Học XML layout
3. Xem [KOTLIN_MainActivity.kt](KOTLIN_MainActivity.kt) - Học Kotlin basics
4. Thực hành build & run

### Nếu bạn đã có kinh nghiệm Android:
1. Nhanh đọc [BRADARGAYX_BUILD_GUIDE.md](BRADARGAYX_BUILD_GUIDE.md)
2. Review 3 Kotlin files
3. Copy files + customize
4. Build release APK

### Nếu bạn chỉ muốn build APK:
1. Copy tất cả files
2. Follow [SETUP_DIRECTORY_STRUCTURE.md](SETUP_DIRECTORY_STRUCTURE.md)
3. Run build: `./gradlew assembleRelease`
4. Done!

---

## 🆘 SUPPORT & TROUBLESHOOTING

### Nếu gặp issue, kiểm tra:

**1. File paths sai?**
- Kiểm tra [SETUP_DIRECTORY_STRUCTURE.md](SETUP_DIRECTORY_STRUCTURE.md) - Directory structure section

**2. JNI crash?**
- Kiểm tra [BRADARGAYX_BUILD_GUIDE.md](BRADARGAYX_BUILD_GUIDE.md) - Section 1.1 "Vấn Đề JNI"
- Verify package name: `com.bradargayx.client`

**3. Build fail?**
- Kiểm tra [GIAI_DOAN_5_BUILD_RELEASE.md](GIAI_DOAN_5_BUILD_RELEASE.md) - Troubleshooting section

**4. UI không đẹp?**
- Kiểm tra [VALUES_colors.xml](VALUES_colors.xml) - Colors
- Kiểm tra [LAYOUT_activity_main.xml](LAYOUT_activity_main.xml) - Layout structure

**5. Setup không hoạt động?**
- Kiểm tra [KOTLIN_AutoSetupManager.kt](KOTLIN_AutoSetupManager.kt) - File paths
- Kiểm tra assets/mods folder tồn tại

---

## 📞 RESOURCES

- [Android Developer Docs](https://developer.android.com)
- [Kotlin Documentation](https://kotlinlang.org/docs)
- [Material Design 3](https://m3.material.io)
- [PojavLauncher GitHub](https://github.com/PojavLauncherTeam/PojavLauncher)

---

## 🎉 SUMMARY

**Bạn có trong tay:**
- ✅ 4 comprehensive guides
- ✅ 10 files XML/Kotlin code đầy đủ
- ✅ 4 build configuration files
- ✅ 3 resource/value files
- ✅ Hướng dẫn từng bước chi tiết
- ✅ Troubleshooting guides

**Tiếp theo:**
1. Clone PojavLauncher fork
2. Follow [SETUP_DIRECTORY_STRUCTURE.md](SETUP_DIRECTORY_STRUCTURE.md)
3. Copy tất cả files
4. Build & test
5. Release app-release.apk

---

**Ready to build BraDargayX? Let's go! 🚀**

*Last updated: 2026-06-01*
*Created with ❤️ for Minecraft enthusiasts*
