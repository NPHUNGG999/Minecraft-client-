-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.bradargayx.client.** { *; }
-keepclassmembers class com.bradargayx.client.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

-dontwarn kotlin.**
-dontwarn kotlinx.**

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
