* package com.devindi.gradle.helper

import org.gradle.api.*
import com.devindi.gradle.helper.task.*
import com.devindi.gradle.helper.dsl.*
import com.devindi.gradle.helper.tools.*

class GitHelperPlugin implements Plugin<Project> {
	void apply(Project project) {
		project.extensions.create("git", GitHelperPluginExtension)
		project.git.extensions.create("hooks", HooksExtension)
		project.task("bumpVersionCode", type: BumpVersionTask)
		project.task("setup", type: InitProjectTask)
		project.task('insertHooks', type: InitProjectTask.InsertGitHooksTask)
		project.task('tagBuild', type: TagBuildTask)
		project.task('bumpVersionName', type: BumpVersionNameTask)
		project.tasks.setup.dependsOn project.tasks.insertHooks
	}

	static String getVersionName(Project project) {
		File versionPropFile = project.file(project.git.versionFile)
		Properties versionProps = PropertyTools.readProperties(versionPropFile)
		return versionProps['VERSION_NAME']
	}

	static Integer getVersionCode(Project project) {
		File versionPropFile = project.file(project.git.versionFile)
		Properties versionProps = PropertyTools.readProperties(versionPropFile)
		return versionProps['VERSION_CODE'].toInteger()
	}
}