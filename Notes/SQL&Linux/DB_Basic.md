# Database Basic

## 查询语句基础

`SELECT * |{[DISTINCT] column | expression [alias], ...} FROM table;`

显示所有列/指定列

> SELECT * / （deptno, dname） FROM dept;

算数表达式和运算符优先级

> SELECT ename, sal, 12 * sal + 100 FROM emp;

> SELECT ename, sal, 12 * (sal + 100) FROM emp;

NULL 值

- 不可用，未分配， 未知且不适用的值

列的别名

- 紧跟列名后面，可使用关键词 AS
- 若别名包含空格或特殊字符，或大小写敏感，需要双引号

> SELECT ename as name, sal salary FROM emp;

> SELECT ENAME "Name", sal*12 "Annual Salary" FROM emp;

级联操作

- 将列或字符串和其他列串联
- 由两条竖线表示

> SELECT ename, job, ename || job as "Employees";

消除重复行

> SELECT DISTINCT deptno FROM emp;

---

## 查询数据的限制和排序

WHERE语句

```
SELECT empno, ename, job, deptno
FROM emp
WHERE deptno = 30;
```

字符串和日期查询（单引号）

```
SELECT ename, job, deptno
FROM emp
WHERE ename = 'RACHEL';
```
---

比较条件

| 操作符 | 描述 |
| ------ | ----|
| = | 等于 |
| >/>= | 大于/大于等于 |
| </<= | 小于/小于等于 |
| <>| 不等于 |
|BETWEEN ... AND ... | 在...和...之间（闭区间）|
|IN(set) | 匹配任何一个值 |
|LIKE | 模糊匹配 |
|IS NULL | 匹配NULL值

```
SELECT ename, job, sal, deptno
FROM emp
WHERE sal <= 1000;
```

```
SELECT ename, job, sal, deptno
FROM emp
WHERE sal between 5000 and 10000;
```

```
SELECT empno, ename, mgr, deptno
FROM emp
WHERE mgr in (7788, 7782, 7000);
```

- 模糊匹配多个S开头的姓名

```
SELECT ename, sal, deptno
FROM emp
WHERE ename LIKE 'S%';
```

- 查询空值

```
SELECT ename, mgr, deptno
FROM emp
WHERE mgr IS NULL;
```
---

逻辑条件 (AND / OR / NOT)

```
SELECT ename, mgr, deptno, sal, job
FROM emp
WHERE sal >= 2000 AND job LIKE '%MAN%';
```

```
SELECT ename, mgr, deptno, sal, job
FROM emp
WHERE sal >= 2000 OR job LIKE '%MAN%';
```

```
SELECT ename, mgr, deptno, sal, job
FROM emp
WHERE job NOT IN('SALESMAN','MANAGER');
```
---
ORDER BY 语句 (默认升序)

```
SELECT ename, job, deptno, hiredate
FROM emp
ORDER BY deptno;
```

- 先根据 deptno 升序排列，再根据 sal 降序排列
```
SELECT ename, job, deptno, hiredate, sal
FROM emp
ORDER BY deptno, sal DESC
```

---

## 典型单行函数

字符函数

- 大小写操作函数

| 函数 | 描述 | 结果 |
| --- | --- | --- |
|LOWER("SQL Course")|转小写|sql course|
|UPPER("SQL Course“)|转大写|SQL COURSE|
|INITCAP("SQL Course”)|单词首字母转大写| Sql Course|

- 字符串操作函数

| 函数 | 描述 | 结果 |
| --- | --- | --- |
|CONCAT('Hello', 'World')|字符连接|HelloWorld|
|SUBSTR('HelloWorld', 1, 5)| 取子串（第一到第五）|Hello|
|LENGTH('HelloWorld')|取子串长度|10|
|INSTR('HelloWorld', 'W')| 返回子字符串在字符串中的位置|6|

---

常用数字函数
- ROUND 四舍五入
- TRUNC 直接舍弃

```
SELECT ROUND(45.926, 2) TRUNC(45.926,2)
FROM dual;

ROUND(45.926, 2) TRUNC(45.926, 2)
---------------- ----------------
    45.93              45.92
```

---

常用日期函数及运算
```
SELECT SYSDATE FROM dual;

SYSDATE
-------
19-AUG-23
```
```
SELECT ename, hiredate, (SYSDATE-hiredate)/7 AS WEEKS, hiredate + 1, hiredate-24/24
FROM emp
WHERE deptno = 30;

ENAME   HIREDATE    WEEKS   HIREDATE+1  HIREDATE-24/24
-----   --------    -----   ----------  --------------
ALLEN   20-FEB-15   25.8517626  21-FEB-15   19-FEB-15
```

---

常用转换函数

![convert.png](convert.png)

```
SELECT ename, hiredate TO_CHAR(hiredate, 'YYYY-MM-DD')
FROM emp

ENAME   HIREDATE    TO_CHAR
-----   --------    -------
SMITH   17-DEC-14   2014-12-17
```

---

常用数字格式类型

|格式类型|描述|
| --- | --- |
| 9 | 一个数字 |
| 0 | 强制显示一个0 |
| $ | 放置一个浮动的美元符号 |
| L | 浮动的本地货币符号 |
| . | 一个小数点 |
| , | 一个‘千’指标符 |

```
SELECT ename, sal, TO_CHAR(sal, '$99,999.00')
FROM emp
WHERE ename = 'RACHEL';

ENAME   SAL     TO_CHAR()
-----   ---     ---------
RACHEL  3000    $3,000.00
```

```
SELECT TO_NUMBER('00001228) FROM dual;

TO_NUMBER('00001228')
-------------
        1228
```

```
SELECT TO_DATE('2015-07-28 19:15:37', 'yyyy-mm-dd hh24:mi:ss') FROM dual;

TO_DATE()
---------
28-JUL-15 19:15:37
```




