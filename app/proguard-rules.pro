# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/akriti/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Gson specific classes
-dontwarn rx.**
-dontwarn com.squareup.okhttp.*
-dontwarn com.google.appengine.api.urlfetch.*
-dontwarn com.octo.android.robospice.retrofit.*
-keep class sun.misc.Unsafe { *; }
-keepattributes *Annotation*
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
@retrofit.http.* <methods>; }
-keepattributes Signature
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
-keep class in.silive.bo.Models.PaperModel {*;}
-keep class in.silive.bo.Network.BytePad {*;}
-keep class com.octo.android.robospice.retrofit.** { *; }
-dontwarn android.support.**
-dontwarn com.sun.xml.internal.**
-dontwarn com.sun.istack.internal.**
-dontwarn org.codehaus.jackson.**
-dontwarn org.springframework.**
-dontwarn java.awt.**
-dontwarn javax.security.**
-dontwarn java.beans.**
-dontwarn javax.xml.**
-dontwarn java.util.**
-dontwarn org.w3c.dom.**
-dontwarn com.google.common.**
-dontwarn com.octo.android.robospice.persistence.**