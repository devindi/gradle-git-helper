package com.devindi.gradle.helper.tools

class HookFactory {

	static List<String> createHook(String name) {
		switch(name) {
			case 'prepare-commit-msg':
			case 'commit-msg':
				return createCommitMessageHook(name)
			break
			default:
				return ['#!/bin/bash']
		}
	}

	private static List<String> createCommitMessageHook(String hookName) {
		return [
			'#!/bin/bash',
			'echo "' + hookName + ' hook"',
			'COMMENT=`cat "$1"`',
			'BRANCH=`git rev-parse --abbrev-ref HEAD`',
			'FIRST_COMMENT_CHAR=${COMMENT:0:1}',
			'if [ "$FIRST_COMMENT_CHAR" = "<" ]; then',
			'	exit',
			'fi',
			'COMMENT="<$BRANCH> $COMMENT"',
			'echo "$COMMENT" > "$1"',
			'echo "' + hookName + ' hook completed"'
		]
	}
}