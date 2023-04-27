# Keys_Seals
帮朋友做的钥匙印章管理系统

## v0.2
1. 新增功能：添加保险柜、删除保险柜
2. 对应UI已进行适配
3. `DepKey`的备用钥匙类型已更正为`int`
4. 第一个可用版本
5. 可利用`configuration.txt`对系统显示进行配置
 
## v0.1
1. 搭建好了数据结构：部门钥匙`DepKey`、后勤钥匙`SupKey`、存储数据库`DB`  
2. 完成了command ui的钥匙界面，能够选择部门显示部门钥匙  
3. DAO完成了`findAll()`以及退出系统写回文件功能`WriteBack()`  
4. 项目结构遵循MVC，分为了`model层`、`view层`、`controller层`，其中`model层`分为`DAO层`、`Service层`    
