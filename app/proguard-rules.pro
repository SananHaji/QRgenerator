# Keep file and line numbers so Crashlytics stack traces stay readable after R8;
# the Crashlytics Gradle plugin uploads the mapping file that de-obfuscates class names.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Play In-App Review references this compile-time-only annotation from play-services-basement,
# which the app does not ship; it has no runtime effect.
-dontwarn com.google.android.gms.common.annotation.NoNullnessRewrite
