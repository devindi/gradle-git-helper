package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.devindi.gradle.helper.tools.*

class InitProjectTask extends DefaultTask {

	@TaskAction
	def init() {
		
		File versionPropFile = project.file(project.git.versionFile)
		FileUtils.writeToFile(versionPropFile, PropertyFactory.createVersionProperties())
		println 'Created ${project.git.versionFile}'
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
			if (project.git.hooks.useBuiltInHooks) {
				FileUtils.writeToFile(hooksFolder, 'prepare-commit-msg', HookFactory.createHook('prepare-commit-msg'))
				println 'prepare-commit-msg inserted'
				FileUtils.writeToFile(hooksFolder, 'commit-msg', HookFactory.createHook('commit-msg'))
				println 'commit-msg inserted'
			}
			project.git.hooks.hooks?.each {
				File src = project.file(it)
				checkOverwrite(src.name)
      			File dst = new File(hooksFolder, src.name)
      			dst.delete()
      			dst << src.text
			}
		}

		void checkOverwrite(String s) {
			if (!project.git.hooks.useBuiltInHooks)
				return
			if (s.equals('prepare-commit-msg'))
				println 'prepare-commit-msg hook will be overwritten'
			if (s.equals('commit-msg'))
				println 'commit-msg hook will be overwritten'	
		}
	}
}