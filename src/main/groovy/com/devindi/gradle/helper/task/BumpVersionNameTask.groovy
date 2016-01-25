package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BumpVersionNameTask extends DefaultTask {

	@TaskAction
	def doBump() {
        File versionPropFile = project.file(project.git.versionFile)
        if (! (versionPropFile.canRead() && versionPropFile.canWrite())) {
            throw new FileNotFoundException("Version file not found by path: ${versionPropFile.getAbsolutePath()}")
        }

        def newVersionName
        println project
        if (!project.hasProperty("newVersionName")) {
            newVersionName = System.console().readLine('> Please enter new version: ')
        } else {
            newVersionName = project.newVersionName
        }
        println "New version is $newVersionName"

        Properties versionProps = new Properties()
        versionProps.load(new FileInputStream(versionPropFile))
        versionProps['VERSION_NAME'] = newVersionName
        versionProps.store(versionPropFile.newWriter(), null)
	}
}