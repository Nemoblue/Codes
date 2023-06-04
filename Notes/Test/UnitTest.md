# Unit Test

## Python 工程目录结构

- `-build`

    构建脚本，使用 makefile 管理安装、测试、发布等

- `-docs`

    使用文档

- `src`

    核心代码，也可以和项目名相同

- `tests`

    测试代码，和 src 目录结构一一对应，发布时不需要带该目录

- `README.md`

    项目介绍

- `requirements.txt`

    记录测试、编译和文档生成所依赖的包信息，同 maven 的 pom.xml 文件

    - 项目引入新包时更新

        `pip freeze > requirements.txt`
    - 其他开发者安装依赖

        `pip install -r requirements.txt`

- `setup.py`

    打包、发布管理

---

## 测试原则 - FIRST

- Fast （快速）

    - 测试的运行速度直接决定自动化的反馈速度，速度越快，越能提早发现问题
    - 反例：

        一个测试用例需要跑一分钟

        ```
        def test_should_run_task_success():
            time.sleep(60)
        ```

    ---

- Isolated （独立）

    - 每个测试用例需要保证完全独立运行，这样才能保证测试运行不被干扰，从而能够进行并行运行甚至分布式运行，使测试运行更加 Fast

    - 反例：

        - 一个测试用例调用了另一个测试用例；
        - 依赖了另一个测试用例的数据

    ---

- Repeatable （可重复）

    - 每个测试用例在运行条件不变的情况下，无论重复运行多少次，运行结果必须完全一致（幂等）

    - 反例：

        - 在没有任何代码变更的情况下，前999次测试用例均通过，但第1000次出错了；
        - 与时间相关，昨天能跑的代码今天不能跑了

    ---

- Self-Validating (自验证)

    - 测试用例必须能够自动告知（断言）运行结果，而不依赖人工进行判断

    - 反例：

        测试只能打印运行日志，需要依赖人工判断运行是否符合预期

    ---

- Timely （及时）

    - 及时编写自动化测试，做到测试先行，直至测试驱动开发（Test-Driven Development）

---

## Python 测试框架

- 业务代码：运行时依赖其他模块、外部组件

- 测试代码：将依赖的部分 mock，由测试框架拉起，并将执行结果汇总报告，生成覆盖率等

    - `TestCase`：测试用例，一个完整的测试流程，检查特定输入下的响应信息

    - `TestSuit`：测试套件，多个测试用例集合在一起

    - `TestFixture`：测试用例环境准备，初始化测试用例、清理释放资源

    - `TestTestRunner`：测试执行器，执行测试用例

- 开发者编写 TestCase，由测试框架的 TestLoader 加载 TestCase 到 TestSuit，由 TestTestRunner 运行 TestSuit，运行结果保存在 TestTestResult

---

## Unittest 测试

- 测试类命名

    `Test{被测类}，并继承 TestCase 类`

- 测试设计
    - `Given` 一个上下文，指定测试预设

    - `When` 进行一系列操作，即所要执行的操作

    - `Then` 得到一系列可观察的结果，即需要检测的断言

- 测试方法命名

    `test_should_{预期行为}_when_{输入条件}`

- 断言

    使用 TestCase 的 `assertXXX` 方法

---

## Unittest 测试执行流程

- `__init__` 方法需要调用基类的 `__init__` 方法，并且指定 method_name 参数

- `setUpClass` 方法在所有用例运行前执行

- `setUp` 方法在每个用例运行前执行

- `test_xxx` 测试用例按 ascii 码顺序执行，各用例应独立，互不干扰

- `tearDown` 方法在每个用例运行后执行

- `tearDownClass` 方法在所有用例运行后执行

    ```
    class TestCurrencyCalculator(TestCase):
        def __init__(self, method_name: str) -> None:
            super(TestCurrencyCalculator, self).__init__(method_name)
            self.calculator: CurrencyCalculator = None
        
        @classmethod
        def setUpClass(cls) -> None:
            print('setUpClass')

        def setUp(self) -> None:
            self.calculator = CurrencyCalculator()
            print('setUp')

        def tearDown(self) -> None:
            self.calculator = None
            print('tearDown')
        
        @classmethod
        def tearDownClass(cls) -> None:
            print('tearDownClass')
    ```

---

## Mock 测试

- Mock 测试作用
    - 解决不同单元间由于耦合而难于测试的问题
    - 通过模拟依赖以分解单元测试耦合的部分
    - 验证所调用的依赖行为

- `unittest.mock.Mock` 类可模拟任意对象（类、类对象、类属性、类方法），`MagicMock` 是 Mock 增强版子类

- Mock 类常用方法

    - `name`：命名一个 mock 对象，标识作用，便于调试
    - `return_value`：设置可调用对象的返回值
    - `side_effect`：设置为 list，多次调用按顺序返回
    - `called`：用于验证该对象是否被调用过
    - `call_count`：用于验证该对象被调用次数
    - `call_args`：用于验证被调用时传入的参数

```
def test_should_return_11_yuan_when_10_btc_by_driver(self):
    mock_driver = mock.MagicMock()
    mock_driver.get_base.return_value = 1

    calculator = CurrencyCalculator()
    calculator.middleware_driver = mock_driver
    self.assertEqual(11, calculator.btc_to_yuan_by_driver(10))
    self.assertEqual(1, mock_driver.get_base.call_count)
```

- Patch 方式 mock
    - 装饰器 `unittest.mock.patch` 指定被 mock 对象的路径，其中 `utils.currency_calculator` 为调用模块路径，`subprocess.run` 为被调用对象（可以是函数，类）
    - 如果需要 mock 多个对象，注意对应关系

```
@mock.patch(`utils.currency_calculator.subprocess.run`, autospec = True)
def test_should_return_right_value_when_calculated_by_tool(self, mock_tool: mock.MagicMock):
    process_mock = mock.MagicMock()
    mock_tool.return_value = process_mock
    process_mock.stdout = b`666`

    calculator = CurrencyCalculator()
    self.assertEqual(666, calculator.btc_to_yuan_by_tool(8))

@mock.patch('module_path3')
@mock.patch('module_path2')
@mock.patch('module_path1')
def test_method_name(self, mock1, mock2, mock3):
    # module_path1 mock 后的对象对应 mock1
    # module_path2 mock 后的对象对应 mock2
    # ...
```

---

## Pytest 测试

- 测试框架会递归查找当前目录下所有以 test 开头或结尾的 python 脚本，并执行文件内所有以 test 开头或结尾的方法
    - 测试方法命名同 unittest
    - 断言：直接使用 `assert` 断言，不需要 assertXXX 方法
        - 用 `pytest.raises` 断言异常

- 参考文档

    [Pytest 官方文档](https:/docs.pytest.org/en/latest/)

- 参数化测试

    允许开发人员使用不同的值反复运行相同的测试用例

    - 装饰器 `pytest.mark.parametrize` 的前两个参数
        - 参数1 为测试用例的参数名列表
        - 参数2 为测试用例执行时传入的参数值列表

    ```
    @pytest.mark.parametrize(['btc', 'expected_yuan'], (0, 0), (1, 1000), (3, 3000))
    def test_should_return_right_yuan_when_input_btc(btc: int, expectted_yuan: int):
        calculator = CurrencyCalculator()
        assert calculator.btc_to_yuan(btc) == expected_yuan
    ```

- 协程测试
    - Python 3.6 版本使用 async/await 语法替代以前的语法
    - 基于普通函数的测试，协程的测试方法
        - 增加 `@pytest.mark.asyncio` 装饰器
        - 使用 `await` 调用被测函数

    ```
    @pytest.mark.asyncio
    async def test_should_return_right_value_when_calculated_tool():
        value = await calculate_by_tool(1000)
        assert value == 1999
    ```

- 跳过用例

    忽略指定测试用例
    - `@pytest.mark.skip(reason = 'out of date')`
    - `@pytest.mark.skipif`

- 预期错误
    
    当条件满足时直接失败，并展示提示信息
    - `@pytest.mark.xfail(gen.__version__ < '0.2.0', reason = 'not support')`

- `Pytest Fixture` 测试固件

    pytest 会在执行测试函数时加载运行 Fixture

    - `yield` 将固件分为两部分， `yield` 之前代码将在测试代码执行前运行， 之后的代码将在测试代码执行后运行，通常用来做数据的预处理和清理

    - `scope` 用于控制固件的作用范围，默认为 `function`
        - `function`：函数级，每个测试函数都会执行
        - `class` 类级，每个测试类执行一次
        - `module` 模块级，每个模块执行一次
        - `session` 会话级，一次测试只执行一次
    
    ```
    @pytest.fixture(scope = 'function')
    def prepare():
        print('before---')
        yield
        print('after---')
    ```

---

## Doctest 测试

docstring 中的交互式测试用例，就像在 Python 会话中执行命令

- 运行方式
    - 直接调用 doctest 模块（不推荐）
         ```
        if __name__ == '__main__':
            import doctest
            doctest.testmod()
         ```
    - 后台命令行调用

    `python -m doctest -v src/utils/calculator_with_doctest.py`

---

## 三种测试框架对比

- `unittest` 内置标准库，用法接近 gtest
- `pytest` 需安装，是 unittest 替代工具，兼容 unittest
    - 语法简单，插件丰富，社区活跃，方便与 CI 系统集成
- `doctest` 需安装，搜索交互式会话的 Python 代码片段，编写较麻烦

---

## 生成覆盖率

- 安装

    `pip install coverage`

- 运行

    - `coverage run XXX.py` 生成统计文件
    - `coverage report` 查看统计结果