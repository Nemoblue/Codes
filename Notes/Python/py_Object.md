# Python Object

## 类（`class`）

- 对象是类的实例（instance）
    - 类也是一个对象
    - 即一个用来创建对象的对象
    - 类是 `type` 类型的对象，定义类实际上就是定义了一个type类型的对象

- 如果多个对象通过一个类创建，我们称这些对象是一类对象

    `a = int(10) # 创建一个int类的实例 等价于 a = 10`

- 自定义的类可以使用大写字母开头，并使用大驼峰命名法（帕斯卡命名法）来对类命名

    ```
    # 定义一个简单的类
    # 使用class关键字来定义类
    # class 类名([父类]):
    #   代码块
    # <class '__main__.MyClass'>
    class MyClass():
        pass
    
    # 使用类来创建对象
    mc = MyClass() # mc就是通过MyClass创建的对象，mc是MyClass的实例
    ```

- 创建对象流程
    - 创建一个变量
    - 在内存中创建一个新对象（公共属性和方法在此处生成并运行）
    - 执行 `__init__(self)` 方法
    - 将对象的id赋值给变量

    ![create](./img/create.png)

- 可以向对象中添加变量，对象中的变量称为属性
    
    语法：对象.属性名 = 属性值

    ```
    mc.name = 'Alice'
    mc_2.name = 'Bob'

    print(mc_2.name)
    ```

- `isinstance()` 用来检查一个对象是否是一个类的实例

    ```
    result = isinstance(mc,MyClass)   # True
    result = isinstance(mc,str)   # False
    ```

---

## 类的定义

- 在类的代码块中可以定义变量和函数
    - `变量` 会成为该类实例的公共属性，可以通过 `对象.属性名` 的形式访问
    - `函数` 会成为该类实例的公共方法，可以通过 `对象.方法名()` 的形式调用方法

    ![class](./img/class.png)

- 方法每次被调用时，解析器会自动传递第一个实参
    - 第一个参数就是调用方法的对象本身，一般命名为 `self`
    - 在方法中不能直接访问类中的属性，需使用 `self.属性名` 传递

    ---
    
- 类对象和实例对象中都可以保存属性（方法）
    - 如果这个属性（方法）是所有的实例共享的，则应该将其保存到类对象中
    - 如果这个属性（方法）是某个实例独有，则应该保存到实例对象中 

- 属性和方法查找的流程
    - 解析器先在当前对象中寻找是否含有该属性
        - 如果有，则直接返回当前的对象的属性值
        - 如果没有，则去当前对象的类对象中去寻找，如果有则返回类对象的属性值，如果类对象中依然没有则报错 

    ```
    class Person:
        name = 'common' # 公共属性，所有实例都可以访问

        def say_hello(self) :
            print('This is %s' %self.name)
    
    # 创建Person的实例
    p1 = Person()
    p2 = Person()

    # 调用方法，对象.方法名()
    p1.say_hello()
    p2.say_hello()

    # 修改 name 属性，指定专有属性
    p1.name = 'Alice'
    p2.name = 'Bob' 

    del p2.name # 删除 p2 的 name 属性
    print(p2.name)  # `common` 
    ```
---

## 类初始化

- 在类中可以定义一些特殊方法（魔术方法）
    - 特殊方法以 `__` 开头，以 `__` 结尾
    - 不要尝试去调用特殊方法
    - 特殊方法将会在特殊的时刻自动调用

- `init()` 会在对象创建以后立刻执行
    - 可以用来向新创建的对象中初始化属性
    - 调用类创建对象时，类后边的所有参数都会依次传递到 `init()` 中

    ```
    class Person:
        def __init__(self, name):
            # 通过self向新建的对象中初始化属性
            self.name = name

        def say_hello(self):
            print('This is %s' % self.name)
    
    p = Person('Alice')
    p.__init__() # 会被再次调用，不要这样做

    p.say_hello()
    ```
---

## 类的基本结构

```
class 类名([父类]) :

    公共的属性... 

    # 对象的初始化方法
    def __init__(self,...):
        ...

    # 其他的方法    
    def method_1(self,...):
        ...

    def method_2(self,...):
        ...

    ...    
```

---

