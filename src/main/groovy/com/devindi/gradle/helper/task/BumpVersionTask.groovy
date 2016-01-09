package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BumpVersionTask extends DefaultTask {

	@TaskAction
	def doBump() {
		println 'Bumping version'
		def versionPropFile = project.file(project.git.versionFile)
		if (! (versionPropFile.canRead() && versionPropFile.canWrite())) {
        	throw new FileNotFoundException("Version file not found by path: ${versionPropFile.getAbsolutePath()}")
    	}
	}
}