package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.ajoberstar.grgit.*

class BumpVersionTask extends DefaultTask {

	@TaskAction
	def doBump() {
		println 'Increment version code'
		File versionPropFile = project.file(project.git.versionFile)
		if (! (versionPropFile.canRead() && versionPropFile.canWrite())) {
        	throw new FileNotFoundException("Version file not found by path: ${versionPropFile.getAbsolutePath()}")
    	}

    	Properties versionProps = new Properties()
    	versionProps.load(new FileInputStream(versionPropFile))
    	int code = versionProps['VERSION_CODE'].toInteger()

        code = code + 1;
        versionProps['VERSION_CODE'] = code.toString()
        versionProps.store(versionPropFile.newWriter(), null)

        def repo = Grgit.open(project.file(project.git.repositoryRoot))
        repo.add(patterns: [versionPropFile.getName()])
        repo.commit(message: "Bump version code to $code")
	}
}