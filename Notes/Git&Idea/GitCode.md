# Git Command

## 工作区

- Repository
- Working Directory
- Stage

## 文件状态

- Committed
- Modified
- Staged

---
## 常用命令

本地目录新建仓库

`git init /e/dir`

克隆

`git clone [URL]`

克隆二进制大文件工程

`git lfs clone [URL]`

---

添加文件

`git add /e/dir/file`

删除文件

`git rm /e/dir/file`

移动/重命名

`git mv file /e/dir`

`git mv file file_new`

---
查看版本（节点/分支）差异

`git diff nodeA nodeB`

`git diff master..e/develop/framework`

查看当前索引修改

`git diff --cached`

查看文件列表改动（参数）

`git diff master..e/develop/framework --name-status`

---
查看工作目录和暂存区（Stage）状态

`git status`

提交到本地版本库(单文件/全部)

`git commit file_name -m "commit message"`

`git commit -am "commit message"`

查看提交历史

`git log --name-status/-p/--pretty/--graph`

提交到远端仓库(注意大小写敏感)

`git push origin branch_name`

`git push origin branch_name:new_branch_name`

---
查看所有分支(本地/远端/所有)

`git branch`

`git branch -r/-a`

新建分支(基于分支e)

`git branch e/new_branch`

新建分支并切换到新分支

`git checkout -b new_branch_name`

删除/强制删除本地分支

`git branch -d/-D branch_name`

删除远端分支(并推送)

`git branch -d -r branch_name`

`git push origin : branch_name`

切换分支(/强制切换)

`git checkout (/-f) branch_name/file_name`

---

远端获取分支更新(/同名)并合并

`git pull origin remote_branch:local_branch`

`git pull origin remote_branch`

远端获取分支更新

`git fetch origin remote_branch:local_branch`

合并其他分支的提交记录（生成新id）

`git cherry-pick commit_id`

合并分支到当前分支(默认，有分叉)

`git merge branch_name`

合并变基（重写项目历史）

`git rebase branch_name`

---
撤销本地提交（回退add/commit，并同时回退master和head）（保留更改并进入暂存区/作为未暂存更改保留/舍弃所有更改）

`git reset (--soft/mixed/hard) commit_id`

回退本地修改但**未提交**的文件（直接覆盖，有风险）

`git checkout .`

`git checkout -filename`

回退到本地提交（仅回退head，master保留）

`git checkout commit_id`

撤销远端提交（撤销任何**单个**提交并添加包含修改的新提交）

`git revert commit_id`

`git push origin : branch_name`

---

查看历史git命令

`git reflog`

将本地文件加入远端仓库

`git remote add origin [URL]`





