# Database Advanced

## 分组函数

```
SELECT group_function(column), ...
FROM table
[WHERE condition];
```

|函数|描述|
|---|---|
|AVG(num)|返回平均值|
|Sum(num)|返回表达式/列总和|
|Count(*)|返回表达式中值的个数|
|Max/Min(num)|返回表达式中最大/最小值|

```
SELECT MAX(sal), MIN(sal), AVG(sal), SUM(sal)
FROM emp
WHERE deptno=30;
```

```
SELECT COUNT(column)
FROM emp
WHERE deptno=30;
```

---

GROUP BY 语句
```
SELECT column, group_function(column)
FROM table
[GROUP BY group_by_expression];
```

```
SELECT deptno, AVG(sal)
FROM emp
GROUP BY deptno;
```

---

HAVING 子句（跟在 GROUP BY 后，且仅对分组有效）

```
SELECT column, group_function(column)
FROM table
[GROUP BY group_by_expression]
[HAVING group_condition]
[ORDER BY column];
```

- 显示每个部门最高薪水大于10k的员工部门号和薪水
- 不显示未满足条件的员工

```
SELECT deptno, MAX(sal)
FROM emp
GROUP BY deptno
HAVING MAX(sal) > 10000
ORDER BY deptno;
```

---

## 多表连接查询

- 自连接
- 等值连接
- 外连接 
- - 左连接
- - 右连接
- - 全连接

自连接查询

```
##查询员工编号，名字及对应经理名字
SELECT work.ename, "works for", mgr.ename
FROM emp work, emp mgr
WHERE work.mgr = mgr.empno (+);
```

等值连接查询

```
SELECT emp.ename, emp.job, dept.deptno, dept.dname
FROM emp, dept
WHERE emp.deptno = dept.deptno;
```

左外连接查询（保留左表未配对值）

```
SELECT emp.ename, emp.job, dept.deptno, dept.dname
FROM emp, dept                      / FROM emp LEFT JOIN dept
WHERE emp.deptno = dept.deptno (+); / ON emp.deptno = dept.deptno;
```

右外连接查询（保留右表未配对值）

```
SELECT emp.ename, emp.job, dept.deptno, dept.dname
FROM emp, dept                      / FROM emp RIGHT JOIN dept
WHERE emp.deptno (+) = dept.deptno; / ON emp.deptno = dept.deptno;
```

全连接查询（未配对值均填空）
- ON 表示过滤条件

```
SELECT emp.ename, emp.job, dept.deptno, dept.dname
FROM emp FULL JOIN dept
ON emp.deptno = dept.deptno;
```

---
## 子查询/嵌套查询

- 查询基于未知信息
- 子查询需在括号内
- 置于比较运算符的右侧
- 对单行/多行子查询使用单行/多行运算符

```
SELECT select_list
FROM table
WHERE expr operator(
    SELECT select_list
    FROM table
    WHERE condition
);
```
---

单行子查询
- 返回结果为单行单列

```
##查询薪水高于RACHEL的职员
SELECT ename, sal
FROM emp
WHERE sal > (
    SELECT sal FROM emp WHERE ename = 'RACHEL'
);
```
```
##查询最低薪水
SELECT ename, sal
FROM emp
WHERE sal = (
    SELECT min(sal) FROM emp
);
```
---

多行子查询

|操作符|描述|
|---|---|
|in|等于列表中任何一个|
|any|和子查询返回任意一个值比较|
|all|和子查询返回所有值比较|

```
##查询所有经理信息
SELECT * FROM emp
WHERE empno IN (
    SELECT mgr FROM emp
);
```

```
##查询薪水低于销售部任意员工的非销售部员工
SELECT empno, ename, job, sal
FROM emp
WHERE sal < ANY(
    SELECT sal
    FROM emp
    WHERE job = 'SALESMAN'
)
    AND job <> 'SALESMAN';
```

```
##空值子查询
SELECT empno, ename, job, sal
FROM emp
WHERE empno IN(
    SELECT mgr FROM emp WHERE job = 'SALES'
)

no rows selected
```











