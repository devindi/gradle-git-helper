package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.ajoberstar.grgit.*
import com.devindi.gradle.helper.tools.PropertyTools

class TagBuildTask extends DefaultTask {

	@TaskAction
	def tagBuild() {
		File versionPropFile = project.file(project.git.versionFile)
		Properties versionProps = PropertyTools.readProperties(versionPropFile)

		def version = "v${versionProps['VERSION_NAME']}-b${versionProps['VERSION_CODE']}"
		def repo = Grgit.open(project.file(project.git.repositoryRoot))
		println version
		println repo

		def tag = repo.tag
		println tag
		repo.tag.add(name: version, annotate: true)
    	//repo.tag.add {
        //	name = version
        //	force = true
    	//}
	}
}