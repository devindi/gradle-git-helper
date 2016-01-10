package com.devindi.gradle.helper.tools

class FileUtils {

	public static void writeToFile(File directory, String fileName, def infoList) {
  		writeToFile(new File(directory, fileName), infoList)
	}

	public static void writeToFile(File file, def infoList)	{
		file.withWriter { out ->
    		infoList.each {
      			out.println it
    		}
  		}
  		file.setExecutable(true)
	}
}