# Python Files

操作文件步骤
- 打开文件
- 对文件进行各种操作（读、写），然后保存
- 关闭文件

---

打开文件

- 使用 `open()` 函数来打开一个文件

    `open(file, mode='r', buffering=-1, encoding_=None, errors=None, newline=None, closefd=True, opener=None)`

- 参数：

    `file` 要打开的文件的名字（路径）

    返回值：

    返回一个 `对象`，这个对象就代表了当前打开的文件

- `open()` 打开文件时，默认是以文本文件的形式打开的，但是 `open()` 默认编码为None，所以处理文本文件时，必须要指定文件的编码
    - 纯文本文件（使用utf-8等编码编写的文本文件）
    - 二进制文件（图片、mp3、ppt等这些文件）
         

    ```
    # 创建一个变量，来保存文件的名字
    # 如果目标文件和当前文件在同一级目录下，则直接使用文件名即可
    file_name = 'demo.txt'

    # 在windows系统使用路径时，可以使用/来代替 \
    # 或者使用 \\ 来代替 \
    # 或者使用原始字符串
    file_name = 'hello\\demo.txt'
    file_name = r'hello\demo.txt'

    # 路径可以使用..来返回上一级目录
    file_name = '../hello/demo.txt'

    # 如果目标文件距离当前文件比较远，此时可以使用绝对路径
    # 绝对路径应该从磁盘的根目录开始书写
    file_name = r'C:\Users\lilichao\Desktop\hello.txt'

    file_obj = open(file_name, encoding='utf-8') # 打开 file_name 对应的文件

    print(file_obj)
    ```

---

关闭文件

- 调用 `close()` 方法来关闭文件

    `file_obj.close()`

- `with ... as` 语句中可以直接使用 file_obj 来做文件操作，此时这个文件只能在 with 中使用，一旦 with 结束则文件会自动 `close()`

    ```
    try:
        with open(file_name) as file_obj :
            print(file_obj.read())
    except FileNotFoundError:
        print(f'{file_name} not exist')
    ```

---

文件读取

- 当我们获取了文件对象以后，所有的对文件的操作都应该通过对象来进行

- `read()` 方法用来读取文件中的内容，它会将内容全部保存为一个字符串返回

    `content = file_obj.read()`

- 直接调用 `read()` 会一次性将文件的内容加载到内存中，容易导致内存泄漏，所以对于较大的文件不要直接调用

- `read()` 可以接收一个 `size` 作为参数，该参数用来指定要读取的字符的数量
    - 默认值为 -1，它会读取文件中的所有字符
    - 每一次读取都是从上次读取到位置开始读取的
    - 如果字符的数量小于 size，则会读取剩余所有的字符
    - 如果已经读取到了文件的最后了，则会返回 `''` 空串
    
    ```
    try:
        with open(file_name, encoding='utf-8') as file_obj:
            # 定义一个变量，来保存文件的内容
            file_content = ''
            # 定义一个变量，来指定每次读取的大小
            chunk = 100
            # 创建一个循环来读取文件内容
            while True:
                # 读取chunk大小的内容
                content = file_obj.read(chunk)

                # 检查是否读取到了内容
                if not content:
                    # 内容读取完毕，退出循环
                    break

                # 输出内容
                # print(content,end='')
                file_content += content
    except FileNotFoundError :
        print(f'{file_name} not exist')
    ```

- `readline()` 用来读取一行内容
- `readlines()` 用于一行一行的读取内容，并一次性将读取到的内容封装到一个列表中返回
    
    ```
    import pprint   # 格式化打印
    import os
    file_name = 'demo.txt'

    with open(file_name , encoding='utf-8') as file_obj:
        # readline()
        print(file_obj.readline(),end='')
        print(file_obj.readline())
        print(file_obj.readline())

        # readlines()
        r = file_obj.readlines()
        pprint.pprint(r[0])
        pprint.pprint(r[1])
        pprint.pprint(r[2])

        # 直接读取
        for t in file_obj:
            print(t)
    ```

- 二进制文件读取
    - `t` 读取文本文件（默认值），size 以**字符**为单位
    - `b` 读取二进制文件，size 以**字节**为单位

    ```
    with open(file_name , 'rb') as file_obj:
        print(file_obj.read(100))

        # 将读取到的内容写出来
        # 定义一个新的文件
        new_name = 'file.flac'

        with open(new_name , 'wb') as new_obj:

            # 定义每次读取的大小
            chunk = 1024 * 100

            while True :
                # 从已有的对象中读取数据
                content = file_obj.read(chunk)

                # 内容读取完毕，终止循环
                if not content :
                    break

                # 将读取到的数据写入到新对象中
                new_obj.write(content)
    ```
---

文件写入

- 使用 `open()` 打开文件时可以指定打开文件所要做的操作（读、写、追加），如果不指定操作类型则默认是读取文件
    - `r` 表示只读
    - `w` 表示可写，使用 w 来写入文件时，如果文件不存在会创建文件，如果文件存在则会**截断文件**，即删除原来文件中的所有内容
    - `a` 表示追加内容，如果文件不存在会创建文件，如果文件存在则会向文件中追加内容
    - `x` 用来新建文件，如果文件不存在则创建，存在则报错（避免覆盖）
    - `+` 为操作符增加功能
        - `r+` 既可读又可写，文件不存在会报错
        - `w+` 在可写上增加可读
        - `a+` 在追加上增加可读

- `write()` 来向文件中写入内容
    - 如果操作的是一个文本文件的话，则需要传递一个字符串作为参数
    - 该方法可以分多次向文件中写入内容
    - 写入完成以后，会返回写入的字符的个数

    ```
    with open(file_name, 'x', encoding='utf-8') as file_obj:
        file_obj.write('aaa\n')

        r = file_obj.write(str(123) + '123123\n')
        r = file_obj.write('New line\n')
        print(r)    # 9
    ```

---

读取文件位置
- `tell()` 方法用来查看当前读取的位置
- `seek()` 可以修改当前读取的位置
    - 参数一：要切换到的位置
    - 参数二：计算位置方式
        - 0 从头计算（默认值）
        - 1 从当前位置计算
        - 2 从最后位置开始计算

    ```
    # 使用二进制读取
    with open('demo.txt','rb') as file_obj:
        print(file_obj.read(100))

        file_obj.seek(55)
        file_obj.seek(80,0)
        file_obj.seek(70,1)
        file_obj.seek(-10,2)

        print(file_obj.read())

        print('当前读取到了 -->',file_obj.tell())
    
    # 使用文本读取，需要考虑字符所占字节数
    with open('demo2.txt','rt' , encoding='utf-8') as file_obj:
        print(file_obj.read(30))

        # 跳过 3 个字符
        file_obj.seek(9)

        print(file_obj.read())
        print('当前读取到了 -->',file_obj.tell())
    ```

---

其他操作

- `os.listdir()` 获取指定目录的目录结构
    - 需要一个路径作为参数，会获取到该路径下的目录结构，默认路径为 `.` 当前目录
    - 该方法会返回一个列表，目录中的每一个文件（夹）的名字都是列表中的一个元素

    ```
    import os
    from pprint import pprint

    r = os.listdir()
    pprint(r)
    ```

- `os.getcwd()` 获取当前所在的目录

- `os.chdir()` 切换当前所在的目录 作用相当于 cd

    ```
    os.chdir('c:/')
    r = os.getcwd()
    ```

- 创建目录

    `os.mkdir("aaa")`

- 删除目录
    
    `os.rmdir('abc')`

- 删除文件

    `os.remove('aa.txt')`

- 重命名 / 移动文件

    ```
    os.rename('aa.txt','bb.txt')
    os.rename('bb.txt','c:/bb.txt')
    ```