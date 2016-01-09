package com.devindi.gradle.helper.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

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
			writeToFile(hooksFolder, 'prepare-commit-msg', prepareHook)
			println 'prepare-commit-msg inserted'
		}

		public void writeToFile(File directory, String fileName, def infoList) {
  			File hookFile = new File(directory, fileName)
  			hookFile.withWriter { out ->
    			infoList.each {
      				out.println it
    			}
  			}
  			hookFile.setExecutable(true)
		}

		List<String> prepareHook = [
		'#!/bin/bash',
		'echo "prepare-commit-msg hook"',
		'COMMENT=`cat "$1"`',
		'BRANCH=`git rev-parse --abbrev-ref HEAD`',
		'FIRST_COMMENT_CHAR=${COMMENT:0:1}',
		'if [ "$FIRST_COMMENT_CHAR" = "<" ]; then',
		'exit',
		'fi',
		'COMMENT="<$BRANCH> $COMMENT"',
		'echo "$COMMENT" > "$1"',
		'echo "prepare-commit-msg hook completed"'
		]
	}
}