package com.xfz.upload

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class AarUploadPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.pluginManager.apply 'maven'
        def aarUploadTask = target.task('aarUpload', type: AarUploadTask, dependsOn: 'assemble')
        //设置源码上传
        aarUploadTask.doFirst {
            target.task(type: Jar) {
                from android.sourceSets.main.java.srcDirs
                classifier = 'sources'
            }
        }
        aarUploadTask.configuration = target.configurations.archives
    }
}