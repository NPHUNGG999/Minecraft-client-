# File: app/proguard-rules.pro
# Mô tả: ProGuard/R8 obfuscation rules cho BraDargayX
#
# Tác dụng:
# - Làm rối (obfuscate) code để khó reverse-engineer
# - Loại bỏ code không dùng
# - Tối ưu APK size
# - GIỮ an toàn native JNI methods

# ===== OPTIMIZATION SETTINGS =====
-optimizationpasses 5
-verbose

# Disable optimization nếu gặp issue
-dontoptimize

# Không preverify (để tránh issue với Android API)
-dontpreverify

# ===== OBFUSCATION RULES =====

# Bật obfuscation dictionary custom (tùy chọn)
-obfuscationdictionary obfuscation_dict.txt
-classobfuscationdictionary obfuscation_dict.txt
-packageobfuscationdictionary obfuscation_dict.txt

# ===== KEEP ANDROID NATIVE METHODS (CRITICAL!) =====
# QUAN TRỌNG: Không obfuscate native methods vì JNI cần tên chính xác!
-keepclasseswithmembernames class * {
    native <methods>;
}

# ===== KEEP MAIN ACTIVITY =====
-keep class com.bradargayx.client.ui.view.MainActivity { *; }

# ===== KEEP MANAGERS =====
-keep class com.bradargayx.client.manager.** { *; }

# ===== KEEP MODEL/DATA CLASSES =====
-keep class com.bradargayx.client.model.** { *; }

# ===== KEEP INTERFACES & CALLBACKS =====
-keep interface com.bradargayx.client.** { *; }

# ===== KEEP ENUM CLASSES =====
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ===== KEEP PARCELABLE CLASSES =====
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# ===== KEEP SERIALIZABLE CLASSES =====
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ===== KEEP FRAGMENT =====
-keep class * extends androidx.fragment.app.Fragment { *; }

# ===== KEEP ANNOTATIONS =====
-keepattributes *Annotation*,
                EnclosingMethod,
                Signature,
                InnerClasses,
                PermissionType

# ===== KEEP SOURCE FILE NAMES & LINE NUMBERS (cho debugging) =====
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ===== REMOVE LOGGING =====
# Loại bỏ tất cả Log.d(), Log.v(), Log.i() trong release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ===== OPTIMIZATION RULES =====
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# ===== FLATTEN HIERARCHY (tùy chọn) =====
# Không dùng nếu gặp issue
# -repackageclasses 'com.bradargayx.client.obfuscated'

# ===== THIRD-PARTY LIBRARY RULES =====

# Gson
-keep class com.google.gson.** { *; }
-keep class ** implements com.google.gson.JsonSerializer
-keep class ** implements com.google.gson.JsonDeserializer

# AndroidX
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-dontwarn androidx.**

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-dontwarn kotlin.**

# ===== POJAVLAUNCHER NATIVE CODE (KEEP!) =====
# Giữ nguyên tất cả code liên quan đến JNI/Native
-keep class net.kdt.pojavlaunch.** { *; }
-keep interface net.kdt.pojavlaunch.** { *; }
-keepclasseswithmembernames class net.kdt.pojavlaunch.** {
    native <methods>;
}

# ===== DEBUGGING & TESTING =====
# Bỏ comment để debug
# -verbose
# -printmapping mapping.txt
# -printconfiguration full_config.txt
# -printusage usage.txt

# ===== WARNING SUPPRESSION =====
-dontwarn java.lang.invoke.StringConcatFactory
-dontwarn com.google.**
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.**
