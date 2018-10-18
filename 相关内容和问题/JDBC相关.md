com.mysql.jdbc.driver时加载mysql-connector-java 5时使用的url
com.mysql.cj.jdbc.Driver是mysql-connector-java 6中的
1. 连接MySQL5：
    driverClassName=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false
    username=root
    password=
2. 连接MySQL6：需要指定时区serverTimezone否则会报错
    driverClassName=com.mysql.cj.jdbc.Driver
    url=jdbc:mysql://localhost:3306/test?userSSL=false&serverTimezone=GMT%2B8
    username=root
    password=

## 注：
在Java项目中必须遵守上面的加载驱动方式才能执行；但在web应用中使用5的驱动也可以加载6，不会出错但是会提示

## url格式
    jdbc:mysql://localhost:3306/ssm
    格式为：
    jdbc:数据库名://ip地址:端口号/数据库名


## 解决web应用运行时找不到jdbc驱动的问题
    详情：ClassNotFound:com.mysql.jdbc.Driver
    解决方法：在Java项目中，只需要导入驱动包就可以进行数据库的连接。但是在web应用中由于web应用运行在Tomcat容器中，所以只在IDE中加载驱动服务器找不到该驱动。把驱动放入Tomcat的lib文件夹下即可解决该问题。

