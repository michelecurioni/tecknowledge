#To undo the last commit from a remote git repository:
```
#on the branch with the unwanted commit
#remove the last commit locally
git reset HEAD^ 
#push it to the remote repo. This is a force push
git push origin +HEAD
```
