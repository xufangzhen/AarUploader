package com.xfz.upload

import org.gradle.api.Plugin
import org.gradle.api.Project

class AarUploadPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.pluginManager.apply 'maven'
        def aarUploadTask = target.task('aarUpload', type: AarUploadTask, dependsOn: 'assemble')
        //获取配置的archives
        aarUploadTask.configuration = target.configurations.archives
    }
}