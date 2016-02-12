package com.devindi.gradle.helper.dsl

class GitHelperPluginExtension {

	String versionFile = './version.properties'
	String repositoryRoot = './..'
	HooksExtension hooksExtension = new HooksExtension()
}