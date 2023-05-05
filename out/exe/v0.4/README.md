# Keys_Seals
帮朋友做的钥匙印章管理系统

## .data文件可以用如下方式进行数据输入
1. 记事本打开，每列数据使用tab（制表符）间隔，一行数据为一个保险柜
2. 后勤部保险柜数据文件supsafes.data中依次输入：部门名称、序号、地点、钥匙数量、入库人签名、出库人签名、备注
3. 部门保险柜数据文件depsafes.data中依次输入：部门名称、序号、店铺、保险柜序号、备用钥匙数量、紧急钥匙数量、入库人签名、出库人签名、备注

## v0.4
1. 新增功能：钥匙入库（测试完毕）、添加后勤部保险柜、删除后勤部保险柜、
2. 放宽了保险柜基本信息的输入限制，如今不需要输入钥匙数量（`DepSafe`：备份钥匙、紧急钥匙；`SupSafe`：钥匙数量）信息
3. 优化了输入输出处理方式，为GUI做准备
4. 新增数据文件后勤部保险柜`supsafe.data`，部门保险柜文件更名为`depsafe.data`

## v0.3
1. `DepKey`->`DepSafe`、`SupKey`->`SupSafe`、`Key`->`Safe`，相关名词正在更改
2. 新增功能：钥匙出库、入库（未测试）

## v0.2
1. 新增功能：添加保险柜、删除保险柜
2. 对应UI已进行适配
3. `DepKey`的备用钥匙类型已更正为`int`
4. 第一个可用版本
5. 可利用`configuration.txt`对系统显示进行配置
6. 新增`key`父类，概括`DepKey`&`SupKey`的共同成员和共同方法
 
## v0.1
1. 搭建好了数据结构：部门钥匙`DepKey`、后勤钥匙`SupKey`、存储数据库`DB`  
2. 完成了command ui的钥匙界面，能够选择部门显示部门钥匙  
3. DAO完成了`findAll()`以及退出系统写回文件功能`WriteBack()`  
4. 项目结构遵循MVC，分为了`model tier`、`view tier`、`controller tier`，其中`model tier`分为`DAO tier`、`Service tier`    
