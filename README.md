# gradle-git-helper

Gradle plugin for managing git hooks and versioning your project.

## Usage

Add the following to your build.gradle

```groovy
buildscript {
	repositories{
	  jcenter()
	}
	dependencies{
		classpath 'com.devindi:git-helper:1.0'
	}
}

apply plugin: 'git-helper'
```

## Configuration

```groovy
git {
  versionFile //relative path to version file. Default value './version.properties'
  repositoryRoot //relative path to git root. Default value './..'
  hooks {
    useBuiltInHooks //flag to manage built-in hooks, boolean. Default value true
    hooks //list of paths to your hooks. Default value []
  }
}
```

## Tasks

Plugin provides next tasks:
* insertHooks - setup git hooks. If 'useBuiltInHooks' is true following hooks will be inserted into .git/hooks :  
```BASH
#prepare-commit-msg
#!/bin/bash
echo "prepare-commit-msg hook"
COMMENT=`cat "$1"`
BRANCH=`git rev-parse --abbrev-ref HEAD`
FIRST_COMMENT_CHAR=${COMMENT:0:1}
if [ "$FIRST_COMMENT_CHAR" = "<" ]; then
	exit
fi
COMMENT="<$BRANCH> $COMMENT"
echo "$COMMENT" > "$1"
echo "prepare-commit-msg hook completed"
```
```BASH
#commit-msg
#!/bin/bash
echo "commit-msg hook"
COMMENT=`cat "$1"`
BRANCH=`git rev-parse --abbrev-ref HEAD`
FIRST_COMMENT_CHAR=${COMMENT:0:1}
if [ "$FIRST_COMMENT_CHAR" = "<" ]; then
	exit
fi
COMMENT="<$BRANCH> $COMMENT"
echo "$COMMENT" > "$1"
echo "commit-msg hook completed"
```
Also that task copies files from 'hooks' list to .git/hooks folder

* setup - creates file to store version code and name. This task depends on insertHooks by default
* bumpVersionCode - increments version code by 1 and commit that change with message 'Bump version code to %new_value%'
* bumpVersionName - change version name. New version name may be a property e.g. 
```groovy
ext.newVersionName = 'v1.2.3'
```
or you may set it as command line argument e.g.
```BASH
./gradlew bumpVersionName -PnewVersionName='v1.2.3'
```
Otherwise task will ask to enter new version name
* tagBuild - creates a tag on last commit with next name '%versionName-b%versionCode%' e.g. 'v1.2.3-b2000'

## Methods

Plugin have next 2 methods:
* GitHelperPlugin.getVersionName(Project project)
* GitHelperPlugin.getVersionCode(Project project)  
```groovy
println com.devindi.gradle.helper.GitHelperPlugin.getVersionName(project)
println com.devindi.gradle.helper.GitHelperPlugin.getVersionCode(project)

//or you can import GitHelperPlugin
import static com.devindi.gradle.helper.GitHelperPlugin.*
println getVersionName(project)
println getVersionCode(project)
```
