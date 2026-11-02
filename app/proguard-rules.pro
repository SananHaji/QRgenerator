# Keep file and line numbers so Crashlytics stack traces stay readable after R8;
# the Crashlytics Gradle plugin uploads the mapping file that de-obfuscates class names.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
