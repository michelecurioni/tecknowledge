## Undo commits
To undo the last commit from a remote git repository, on the branch with the unwanted commit:
1. remove the last commit locally `git reset HEAD^`
2. push it to the remote repo. This is a force push `git push origin +HEAD`

## highlight space differences
Using git diff in reverse `git diff -R` , whitespaces will be highlighted in red. Bear in mind that red lines are the latest version.  
Another option is to use `git diff --ws-error-highlight=all`
