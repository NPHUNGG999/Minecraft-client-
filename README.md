# 🎮 BraDargayX Client - Complete Build Kit

**Welcome, Senior Developer!** 👋

Bạn sắp xây dựng **BraDargayX Client** - một ứng dụng Launcher sang trọng cho Minecraft Fabric 1.21.1.

---

## ⚡ QUICK START (5 phút)

### 1️⃣ Đọc file này
```bash
ls -la
# Bạn sẽ thấy 22 files trong workspace này
```

### 2️⃣ Mở INDEX.md
```bash
cat INDEX.md  # hoặc mở trong VS Code
```

### 3️⃣ Follow hướng dẫn
- **Hướng dẫn chính:** BRADARGAYX_BUILD_GUIDE.md
- **Setup dự án:** SETUP_DIRECTORY_STRUCTURE.md
- **Lock khởi chạy:** GIAI_DOAN_4_FORCE_LAUNCH.md
- **Build release:** GIAI_DOAN_5_BUILD_RELEASE.md

---

## 📦 WHAT'S IN THE BOX?

### Hướng Dẫn (4 files)
| File | Mục đích |
|------|---------|
| `BRADARGAYX_BUILD_GUIDE.md` | **START HERE!** Hướng dẫn 5 giai đoạn chính |
| `SETUP_DIRECTORY_STRUCTURE.md` | Cách tổ chức files & thao tác copy |
| `GIAI_DOAN_4_FORCE_LAUNCH.md` | Chi tiết: Lock version khởi chạy |
| `GIAI_DOAN_5_BUILD_RELEASE.md` | Chi tiết: ProGuard + Release APK |

### Code & XML (10 files)
**Kotlin:**
- `KOTLIN_MainActivity.kt` - UI chính + setup flow
- `KOTLIN_AutoSetupManager.kt` - Copy mods từ assets
- `KOTLIN_GameLaunchManager.kt` - Force launch version

**Layout & Drawable:**
- `LAYOUT_activity_main.xml` - Giao diện Luxury Modern
- `DRAWABLE_shape_gradient_button.xml` - Gradient button
- `DRAWABLE_ripple_effect.xml` - Ripple effect khi chạm
- `ANIMATOR_btn_state_animator.xml` - Button animation

**Resources:**
- `XML_data_extraction_rules.xml` - Backup rules (Android 12+)
- `XML_backup_descriptor.xml` - Backup rules (Android 11-)

### Build Config (4 files)
- `BUILD_app_build.gradle` - Build configuration
- `PROGUARD_proguard_rules.pro` - Obfuscation rules
- `OBFUSCATION_obfuscation_dict.txt` - Dictionary
- `MANIFEST_AndroidManifest.xml` - App manifest

### Values (3 files)
- `VALUES_strings.xml` - String resources
- `VALUES_colors.xml` - Color palette (Dark theme)
- `VALUES_styles.xml` - Theme & styles

### Reference (1 file)
- `FILES_COMPLETE_CHECKLIST.md` - Danh sách tất cả files

---

## 🎯 YOUR MISSION

```
┌─────────────────────────────────────────────────────────┐
│                                                           │
│  Transform PojavLauncher v3 fork into                    │
│  🎨 Beautiful, minimalist BraDargayX Launcher           │
│                                                           │
│  ✨ Features:                                            │
│     • Luxury Modern UI (Dark, Glassy, Rounded)          │
│     • Auto-setup: Copy mods from assets                 │
│     • Force launch: fabric-loader-0.16.x-1.21.1         │
│     • Obfuscated + Release-ready                         │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

**End result:** `BraDargayX-Release.apk` ready to install on any Android device.

---

## 🚀 TIMELINE

```
Week 1: Clone fork + understand architecture
Week 2: Copy files + configure gradle
Week 3: Test + integrate AutoSetupManager
Week 4: Build release + distribute
```

---

## 🎓 5 GIAI ĐOẠN

### ✅ GIAI ĐOẠN 1: Gradle & Application ID
- Đổi `applicationId` → `com.bradargayx.client`
- Tạo icons ở tất cả độ phân giải
- ⚠️ **CRITICAL:** Giữ nguyên NDK (JNI không bị hỏng)

**File:** `BUILD_app_build.gradle`

### ✨ GIAI ĐOẠN 2: Luxury Launcher UI
- Dark theme: #121212
- Gradient button: Tím (#B388FF) → Cyan (#00E5FF)
- Chỉ hiển thị: Logo + Status + PLAY button
- Ripple effect khi chạm

**Files:** `LAYOUT_activity_main.xml`, `DRAWABLE_*.xml`, `VALUES_colors.xml`

### 📦 GIAI ĐOẠN 3: Bundle Assets & Auto Setup
- Copy `bradargayx.jar` & `fabric-api.jar` từ assets
- Automatic extraction trên first launch
- Progress bar UI
- SharedPreferences tracking

**Files:** `KOTLIN_AutoSetupManager.kt`, `KOTLIN_MainActivity.kt`

### 🎮 GIAI ĐOẠN 4: Force Launch Logic
- Khóa version: "fabric-loader-0.16.x-1.21.1"
- Bỏ qua version spinner UI
- JNI integration dengan PojavLauncher

**Files:** `KOTLIN_GameLaunchManager.kt`, `GIAI_DOAN_4_FORCE_LAUNCH.md`

### 📤 GIAI ĐOẠN 5: Optimize & Build Release
- R8 obfuscation (làm rối code)
- Keystore generation (chữ ký số)
- Release APK build + signing
- ProGuard rules (keep native methods!)

**Files:** `PROGUARD_proguard_rules.pro`, `GIAI_DOAN_5_BUILD_RELEASE.md`

---

## 📋 BEFORE YOU START

### ✅ Prerequisites

```
- Android Studio 2021.1+
- JDK 11+
- NDK (via SDK Manager)
- PojavLauncher v3 fork (clone từ GitHub)
- bradargayx.jar (your custom client)
- fabric-api.jar (1.21.1)
- Icon 512×512 (for app icon)
- Montserrat font (from Google Fonts)
```

### ⚠️ Important Notes

1. **Package Name:** Thay đổi từ `net.kdt.pojavlaunch` → `com.bradargayx.client`
   - JNI sẽ bị hỏng nếu đổi sai!
   - Đọc phần "Vấn Đề JNI" trong BRADARGAYX_BUILD_GUIDE.md

2. **ProGuard Rules:** Không obfuscate native methods!
   - `native <methods>` MUST be kept
   - Xem `PROGUARD_proguard_rules.pro`

3. **Keystore:** Backup an toàn!
   - Nếu mất, không thể update app
   - Lưu vào USB drive encrypted

4. **Test:** Trên thiết bị thực, không chỉ emulator
   - JNI + Performance khác nhau

---

## 📖 READING ORDER

### Đối với người mới:
1. **INDEX.md** - Overview tất cả files
2. **BRADARGAYX_BUILD_GUIDE.md** - Hướng dẫn chính
3. **SETUP_DIRECTORY_STRUCTURE.md** - Cách setup dự án
4. Thực hành từng giai đoạn

### Đối với experienced devs:
1. Review 3 Kotlin files
2. Copy tất cả XML/build configs
3. Customize + build
4. Done!

---

## 🎯 NEXT IMMEDIATE STEPS

### RIGHT NOW:
```bash
# 1. Navigate to this directory
cd /workspaces/Minecraft-client-

# 2. List all files
ls -la

# 3. Open INDEX.md
cat INDEX.md | less

# 4. Or in VS Code, click: INDEX.md
```

### THEN:
```bash
# 1. Read BRADARGAYX_BUILD_GUIDE.md
code BRADARGAYX_BUILD_GUIDE.md

# 2. Clone your PojavLauncher fork
git clone <your_fork_url> ~/my-bradargayx-project

# 3. Follow SETUP_DIRECTORY_STRUCTURE.md
# (Create dirs, copy files, etc.)
```

---

## 💡 PRO TIPS

- **All comments in Vietnamese** - dễ dàng theo dõi
- **Code examples include explanations** - không "magic"
- **Each file có detailed headers** - biết file dùng cho cái gì
- **Troubleshooting included** - nếu gặp issue
- **Quick commands provided** - copy-paste ready

---

## 🔗 IMPORTANT FILES TO READ FIRST

### 1️⃣ `INDEX.md` - Master index
📖 Danh sách tất cả files, cách navigate

### 2️⃣ `BRADARGAYX_BUILD_GUIDE.md` - Main guide
📚 Hướng dẫn 5 giai đoạn chi tiết

### 3️⃣ `SETUP_DIRECTORY_STRUCTURE.md` - Setup guide
🗂️ Cách organize files, lệnh copy

---

## 🎮 FINAL DELIVERABLE

```
After following all steps:

✅ BraDargayX-Release.apk
   - Luxury Modern UI (Dark, Glassy)
   - Auto-setup mods from assets
   - Forced launch: Fabric 1.21.1
   - Obfuscated code
   - Ready for distribution
   
Ready to install on:
  📱 Android 8.0+ (API 26+)
  🎮 Any phone with 2GB+ RAM
```

---

## 📞 COMMON ISSUES

| Issue | Solution |
|-------|----------|
| "JNI crash" | Check package name change (Section 1.1) |
| "Setup fails" | Check assets/mods folder exists |
| "UI not pretty" | Review VALUES_colors.xml & LAYOUT |
| "Build fails" | Check NDK installed via SDK Manager |
| "APK too big" | Enable shrinkResources in build.gradle |

→ Full troubleshooting in **GIAI_DOAN_5_BUILD_RELEASE.md**

---

## 🏆 YOU GOT THIS!

```
      _____      ____
     / ___/     / __ \
     \__ \     / / / /
    ___/ /    / /_/ /
   /____/     \___\_\

BraDargayX Client Builder
v1.0.0 - Ready to Build!
```

---

## ✨ QUICK LINKS

📖 **INDEX.md** - Start here!
🛠️ **SETUP_DIRECTORY_STRUCTURE.md** - Setup guide
🎨 **BRADARGAYX_BUILD_GUIDE.md** - Main guide
🔧 **GIAI_DOAN_4_FORCE_LAUNCH.md** - Lock version
📤 **GIAI_DOAN_5_BUILD_RELEASE.md** - Build release
✅ **FILES_COMPLETE_CHECKLIST.md** - All files list

---

**Let's build something amazing! 🚀**

*Questions? Check the guides. All answers are there.*

*Good luck!* 🎮

---

**Created with ❤️ for Minecraft enthusiasts**
**Last Updated: 2026-06-01**
