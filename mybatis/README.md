SQL规范

git地址格式 #注当前上线走分支，分支命名规范：环境名-年月日，如"dmc-20160902"
git@192.168.11.19:damaichang/dmcmysql.git 分支【deploy/数据库名称/【ddl-数据库名称-表名.sql，dml-数据库名称-表名.sql】】
git@192.168.11.19:damaichang/dmcmysql.git 分支【rollback/数据库名称/【rollback-数据库名称-表名.sql】】
注意事项：
sql文件命名可以后在【dml|ddl】-数据库名称-表名-【标识】.sql加一些特殊标
举例：
git@192.168.11.19:damaichang/dmcmysql.git【分支  deploy/dbname/【ddl-数据库名称-表名.sql，dml-数据库名称-表名.sql】】
git@192.168.11.19:damaichang/bimysql.git【分支 deploy/dbname/【ddl-数据库名称-表名.sql，dml-数据库名称-表名.sql】 】
文件命名格式：【ddl|dml】_数据库名称_表名.sql
说明：
DDL（data definition language）是数据定义语言：DDL比DML要多，主要的命令有CREATE、ALTER、DROP等，DDL主要是用在定义或改变表（TABLE）的结构，数据类型，表之间的链接和约束等初始化工作上，他们大多在建立表时使用。
DML（data manipulation language）是数据操纵语言：它们是SELECT、UPDATE、INSERT、DELETE，就象它的名字一样，这4条命令是用来对数据库里的数据进行操作的语言。
文件数量
#每一个表操作，最多同时拥有一个ddl&一个dml类型文件。
sql文件标准 #插入数据
sql语句必须准确指定：【数据库名.表名】进行数据操作
文本字符格式，统一为utf8
必须为标准sql文件，能正常通过mysql语法导入
ddl与dml脚本必须非常严谨，如必要的条件判断...
create index if not exists
insert where not exists
#原则，多次重复执行sql文件不影响结果 
一定要有回滚脚本【参考Inception思维】
环境名称说明
环境名称	说明	备注
dmcmysql	存放平台业务，如:wms-db，uc-db这类数据库	
bimysql	存放bi业务,如:bi-db这类数据库	


