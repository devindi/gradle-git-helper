package com.devindi.gradle.helper.tools

class PropertyTools {

	static Properties readProperties(File file) {
		Properties props = new Properties()
    	props.load(new FileInputStream(file))
    	return props
	}
}