# Linux Manual

## 开发基础

查看操作系统

`cat /proc/version`

查看系统时间

`date`

查看IO，系统内存，CPU，监控视图等

- 查看CPU详细信息

    `cat /proc/cpuinfo`

- 查看内存使用情况

    `free -m`

- 监控视图

    `top`
- 输出CPU和磁盘IO相关统计信息

    `lostat`

查看网络端口状态
- 查看18008端口状态

    `netstat -anp | grep 18008`

---

## 场景：更换 Linux 环境下某个jar包

进入某个目录

` cd /opt/oss`

查找某个具体的文件
- 当前目录下查询以test开头的文件

    `find . -name "test*"`
- 根目录下查询以test开头的文件

    `find / -name "test*"`

修改文件权限和修改文件名

- 修改单个文件权限

    `chown ossuser:ossgroup test.jar`
- 递归修改test目录下的文件

    `chown -R ossuser:ossgroup test/`

- 重命名和移动
    `mv`

- 拷贝
    `cp`

从一个环境上copy包到当前环境具体目录

- 把当前目录的test.jar复制到远端环境的目录下

    `scp test.jar root@10.10.10.10:/tmp/test`

搜索进程，杀死进程

- 查看包含test的进程

    `ps -ef | grep test`

- 强制杀死进程
    
    `kill -9 pid`

---

## 正则表达式

常用于以下命令

- grep 工具组，grep 和 egrep
- sed 流编辑器
- 字符串处理程序语言 awk
- 文件查询程序 more，less
- 文本编辑器 vi
- `BRE` 仅为基本正则表达式，`ERE` 为扩展表达式

|字符|意义|支持类型|
|---|---|---|
|`\`|转义字符，如 `\n` 匹配换行符, `\\` 匹配 `\`| BOTH|
|`^`|匹配输入字符串开始位置| BOTH|
|`$`|匹配输入字符串结束位置| BOTH|
|`*`|匹配前面的子表达式0次或多次， 如 `zo*` 匹配 `z` 和 `zoo`| BOTH|
|`?`|匹配前面的子表达式0次或一次| `ERE`|
|`.`|匹配除 `\n` 之外的任何单个字符| BOTH|
|`+`|匹配前面的子表达式一次或多次，如 `zo+` 不能匹配 `z`| `ERE`|
|`\|`|匹配于 `\|` 符号前或后的正则表达式，即 `or`| `ERE`|
|`[xyz]`|匹配 xyz 中任何一个字符| BOTH|
|`[^xyz]`|匹配不包含在 xyz 中的任意字符| BOTH|
|`[x-z]`|匹配小写字符| BOTH|
|次数匹配|||
|`{n,m}`|最少匹配n次，最多匹配m次，和 `BRE` 的区别是不需要加 `\`| `ERE`|
|`\{n\}`|匹配前面的子表达式n次| BRE|
|`\{n,\}`|匹配前面的子表达式至少n次| BRE|
|`\{n,m\}`|最少匹配n次，最多匹配m次| BRE|
|后向引用|||
|`\(\)`|将 `\(` 与 `\)` 之间的模式存储在特殊的保存空间| BRE|
|`\digit`|匹配于第n (1-9) 个先前括号 (`\(` 和 `\)`)内子表达式匹配成功的字符| BRE|

---

常见例子
- 查找test.txt中包含3个a的行
    
    `grep 'a\{3\}' test.txt`

- 查找test.txt中以aa开头的行

    `grep '^aa' test.txt`

- 查找所有空行

    `grep '^$' test.txt`

后向引用
- 指匹配 **于正则表达式匹配的先前部分**
- 仅在 BRE 中存在
- 步骤如下
    - 将子表达式包围在 `\(` 和 `\)` 里，单个模式里可包含最多9个子表达式
    - 在模式之后使用 `\digit` ，digit 为介于 1 到 9 的数字，指代 **匹配于第n个先前括号内子表达式匹配成功的字符**
- 可以匹配 test.txt 中的 `why..why`

    `grep '\(why\).*\1' test.txt`

- 可以匹配 test.txt 中的 `abcddcdab`

    `grep '\(ab\)\(cd\)[def]*\2\1' test.txt`

---

## grep 命令使用

> grep [-option] pattern file

常用参数

- -v 过滤指定字符串内容的行
- -i 不区分大小写
- -n 顺便打印行号
- -c 计算出符合行的次数
- -E 加上支持扩展的正则表达式，有 '+', '?','|','()' 四种，同命令 `egrep`

常用命令

- 查询进程并过滤grep命令

    `ps -elf | grep -v grep | grep "necomm_agent"`

- 找出test.txt里包含abc的行，忽略大小写

    `grep -i 'abc' test.txt`

- 过滤掉test.txt里的空行

    `grep -v '^$' text.txt > testnew.txt`

---

## sed 命令使用（编辑文本）

> sed [-option] [sed-commands] [input-file]

常用参数

- -n 抑制默认输出
- -e 执行多条编辑命令
- -i 直接在源文件中修改
- sed-commands 可以是多个sed命令组合
- input-file 可选，sed 也可以从标准输入（如管道）获取输入

工作原理

- sed 从文件或管道中读取一行放在模式空间中处理，处理完输出一行再读取一行
- 模式空间：sed内部的临时缓存，用于存放读取到的内容

增加文本 （`a` 追加到行后 | `i` 插入到行前）

- 单行增加

    `sed '2a abc' test.txt`

    `sed '2i abc' test.txt`

- 多行增加
    ```
    sed '2a abc\

    cde' test.txt
    ```

- 如果在sed命令前不指定地址范围则匹配所有行

    `n1[,n2] {sed-commands}`

    - n1, n2 可以是数字，正则表达式或两者组合，`$` 表示最后一行
    - 对匹配 abc 的行到匹配 fff 的行的操作

        `/abc/,/fff/{sed-commands}`

删除文本（`d` 删除指定行）

- 删除指定的行

    `sed '/aaa/d' test.txt`

修改文本（`c` 用新行取代旧行 | `s` 进行文本替换）

- 用新行取代旧行

    `sed '2c abc' test.txt`

- 将所有 abc 替换为 bcd，`g` 进行全局替换，否则只替换每行第一个匹配的字符串
        
    `sed 's/abc/bcd/g' test.txt`

- 在 text.txt 每行前插入 abc

    `sed 's/^/abc/' test.txt`

- 仅在匹配 aaa 的行进行文本替换，`-i` 代表修改了源文件 test.txt

    `sed -i '/aaa/s/abc/bcd/g' test.txt`

- 将匹配d内容的行中的ab替换为abjj， `&` 表示被pattern匹配的内容

    `sed '/d/s/ab/&jj/' test.txt`

- 将命令中的digit 7 修改为 7， `\num` 表示被pattern匹配的第num个分组

    `echo this is digit 7 in a number | sed 's/digit \{[0-9]\}\1'`

查找文本（`p` 输出指定内容）

- 按行查询，默认输出2次匹配的结果，因此用 `n` 取消默认输出

    `sed -n '2p' test.txt`

- 按字符串查询

    `sed -n '/abc/p' test.txt`

- 混合查询

    `sed -n '2,/abc/p' test.txt`

多命令混合编辑

`sed -e '1,5d' -e 's/test/check/' test.txt`

---

## awk 命令（切片分析）

> awk [-F field-separator] '{pattern + action}' {filename}

- pattern 表示 awk 在数据库中查找的内容
- action 表示找到匹配内容所执行的一系列命令
- `-F` 可选，默认的域分隔符是空格
- 例子： `awk '{print $1}'` 打印读到的第一个域

执行流程

```
cat /etc/passwd | \
awk -F ':' 'BEGIN {print "name, shell} {print $1","$7} END {print "blue, /bin/nosh"}'
```

- 执行BEGIN后的命令
- 读取文件，读入第一行记录（即`\n`分割的记录）
- 在该行按指定的分隔符 `:` 划分域
- 填充域，`$0` 表示所有域，`$1` 表示第一个域，`$n` 表示第n个域
- 执行模式所对应的动作
- 读入第二条记录，直至所有记录都读完
- 执行END后的操作

常用命令

- 输出匹配到的每行内容（未指定action）
    
    `awk -F: '/^root/' /etc/passwd`

- 输出匹配到的指定域内容（指定action）

    `awk -F: '/^ftp/{print $4}' /etc/passwd`

    ```
    ls -l | \
    awk '/^[^d]/ \
    {print $9"\t"$5} {tot+=$5} \
    END {print "totKB:" tot}'
    ```

- 统计某个文件夹下文件占用的字节数

    ```
    ls -l | \
    awk 'BEGIN {size=0;} \
    {size = size + $5;} \
    END {print "[end]size is ", size/1024, 1024, "M"}'
    ```

- 输出test.txt中的姓名和最大成绩

    ```
    awk 'BEGIN {MAX = 0;} \
    {NAME = $1; \
    for (x = 2; x <= NF; x++) { \
    if ($x >= MAX) {MAX = $x}} \
    printf "name: %s MAX: %d\n", NAME, MAX; MAX = 0}' \
    test.txt
    ```

---

## 各类常用命令

扫描本地 IP 地址

`ifconfig eth0 | grep "inet addr:" | awk '{print $2}' | sed 's/addr://g'`

扫描端口号被哪个进程占用

```
netstat -anp | \
awk '{print $4,$7}' | grep 31006 | \
sed -n '1p' | awk '{print $2}' | \
awk -F '/' '{print $1}' | \
xargs ps -lfp | \
sed -n '2p' | awk '{print $17}'
```

获取某个进程的堆栈

```
ps -elf | \
grep -v grep | \
grep necomm_agent | \
awk '{print $4}' | \
xargs gstack > yp.txt
```

批量重命名网元license备份文件名

```
for i in 'ls'; \
do mv -f $i 'echo $i | \

## 正则表达式(使用ERE格式) 
## ^[a-zA-Z0-9].*_[a-zA-Z0-9].*_([0-9]{8})(_)([0-9]{6})(.txt)$

sed 's/^[a-zA-Z0-9].*_[a-zA-Z0-9].*_\([0-9]\{8\}\)\(_\)\([0-9]\{6\}\)\(\.txt\)$/Lic_Esn_\1\2\3/'; \
done
```
xargs 相关

- 若命令本身不支持管道，需要加xargs
- 例如：输出/opt下目录的内容 `echo "/opt" | xargs ls -l`
- 本身支持管道的命令有 `cut`， `grep`， `sort`， `uniq`， `wc`， `tee`， `join`， `split` 等

- 即使命令支持管道，也可以使用xargs改变命令输出方式
    - `find ./ | grep "abc"`
        - 命令不会在目录下文件内查找相关内容
        - 仅在一群文件名中查找是否有包含 abc 的文件名
    - `find ./ | xargs grep "abc"`
        - find出来的内容会作为参数传递给管道后的内容
        - 会在目录下的文件内查找相关内容
        - 即在find出来的文件中查找是否包含字符串 abc
