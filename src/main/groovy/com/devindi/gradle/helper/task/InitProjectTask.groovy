package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.devindi.gradle.helper.tools.HookFactory
import com.devindi.gradle.helper.tools.FileUtils

class InitProjectTask extends DefaultTask {

	@TaskAction
	def init() {
		println 'Initing project'
		def versionPropFile = project.file(project.git.versionFile)
		if (! (versionPropFile.canRead() && versionPropFile.canWrite())) {
        	throw new FileNotFoundException("Version file not found by path: ${versionPropFile.getAbsolutePath()}")
    	}
	}

	static class InsertGitHooksTask extends DefaultTask {

		@TaskAction
		def insertHooks() {
			File repositoryRootFolder = project.file(project.git.repositoryRoot)
			def gitFolder = new File(repositoryRootFolder, '.git')
			if (!(gitFolder.exists() && gitFolder.isDirectory())) {
				throw new IllegalStateException("Repository root $repositoryRootFolder is incorrect. .git folder not found")
			}
			File hooksFolder = new File (gitFolder, 'hooks')
			FileUtils.writeToFile(hooksFolder, 'prepare-commit-msg', HookFactory.createHook('prepare-commit-msg'))
			println 'prepare-commit-msg inserted'
			FileUtils.writeToFile(hooksFolder, 'commit-msg', HookFactory.createHook('commit-msg'))
			println 'commit-msg inserted'
		}
	}
}