package com.example.plugin

import com.example.plugin.tasks.JavadocTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildExtensionPlugin implements Plugin<Project> {
    final String CUSTOM_GROUP = 'publish lib'

    @Override
    void apply(Project project) {
        project.tasks.create("customJavadoc", JavadocTask)
    }
}