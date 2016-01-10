package com.devindi.gradle.helper.tools

class FileUtils {
	
	public static void writeToFile(File directory, String fileName, def infoList) {
  			File hookFile = new File(directory, fileName)
  			hookFile.withWriter { out ->
    			infoList.each {
      				out.println it
    			}
  			}
  			hookFile.setExecutable(true)
		}
}