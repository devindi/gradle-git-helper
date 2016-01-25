package com.devindi.gradle.helper

import org.gradle.api.*
import com.devindi.gradle.helper.task.*
import com.devindi.gradle.helper.dsl.*

class GitHelperPlugin implements Plugin<Project> {
	void apply(Project project) {
		println "Applying plugin"
		project.extensions.create("git", GitHelperPluginExtension)
		println "Extension added"
		project.task("bumpVersionCode", type: BumpVersionTask)
		project.task("setupGit", type: InitProjectTask)
		project.task('insertHooks', type: InitProjectTask.InsertGitHooksTask)
		project.task('tagBuild', type: TagBuildTask)
		project.task('bumpVersionName', type: BumpVersionNameTask)
		println "Task added"

		project.tasks.setupGit.dependsOn project.tasks.insertHooks
	}
}