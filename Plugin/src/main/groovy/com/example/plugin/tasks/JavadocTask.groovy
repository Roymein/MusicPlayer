package com.example.plugin.tasks

import com.example.plugin.PluginConstants
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.javadoc.Javadoc

class JavadocTask extends Javadoc{

    JavadocTask() {
        group = PluginConstants.CUSTOM_GROUP_ID
        description = ""
    }

    @TaskAction
    void configJavadoc() {
        this.options {
            locale 'en_US'
            encoding('UTF-8')
            charSet('UTF-8')
            memberLevel = JavadocMemberLevel.PUBLIC
            linkSource true
            author true
            noTree true
            noSince false
            noIndex false
            noNavBar false
            noHelp true
            noDeprecated false
            noDeprecatedList false
            noComment false
            links "http://docs.oracle.com/javase/7/docs/api"
        }

        this.include '**/BuildConfig.java', '**/R.java', '**/*.aidl'
        source = android.sourceSets.main.java.srcDirs
        source += files(project.buildDir.absolutePath + File.separator +
                "generated/aidl_source_output_dir/compatDebug/out/android/app/IWallpaperManagerCallbackNative.java")
        source += files(project.buildDir.absolutePath + File.separator +
                "generated/aidl_source_output_dir/compatDebug/out/com/android/internal/telephony/ITelephony.java")
        destinationDir = dest
        failOnError false
        classpath += files("${android.sdkDirectory}/platforms/${android.compileSdkVersion}/android.jar")
        afterEvaluate {
            classpath += files(
                    android.libraryVariants.collect { variant ->
                        variant.javaCompileProvider.get().classpath.files
                    })
        }
    }
}