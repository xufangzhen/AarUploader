package com.xfz.upload

import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Upload

class AarUploadTask extends Upload {
    String groupId = null
    String artifactId = null
    String version = null

    @TaskAction
    protected void upload() {
        if (groupId == null || artifactId == null) return
        //配置仓库，该demo设置本地maven
        repositories {
            mavenDeployer {
                /*
                repository(url: releaseRepo) {
                    authentication(userName: username, password: password)
                }
                snapshotRepository(url: snapshotRepoUrl) {
                    authentication(userName: username, password: password)
                }
                */
                repository url: "file:///Users/xufangzhen/.m2/repository"
                //计算版本信息，如果是snapshot版本，就直接拿版本号，如果是正式版，拿最后tag作为版本号
                pom.version = getVersionName()
                pom.artifactId = artifactId
                pom.groupId = groupId
            }
        }
        //开始上传
        println "start upload " + groupId + ":" + artifactId + ":" + version
        super.upload()
    }

    /**
     * ./gradlew clean -PisSnapshot=false :klui:aarUpload
     * 使用如上的命令打包来上传 klui.aar
     **/
    def getVersionName() {
        def isSnapshot = ""
        if (project.hasProperty("isSnapshot")) {
            isSnapshot = project.property("isSnapshot")
        }
        //如果是snapshot且版本号不为空，就用版本号-SNAPSHOT命名
        if (Boolean.parseBoolean(isSnapshot) && version != null) {
            return version + '-SNAPSHOT'
        } else {
            //否则使用最后提交的tag名命名
            return 'git describe --tags --abbrev=0'.execute().text.trim()
        }
    }
}