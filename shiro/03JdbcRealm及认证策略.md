使用shiro框架来完成认证工作，默认情况下是使用IniRealm。如果需要使用其他Realm，需要进行相关的配置。
# ini配置文件
1. [main]：配置应用程序的SecurityManager实例及任何它依赖组件（如Realm）的地方。
2. [users]：配置账户的相关信息，用户名、密码、角色等相关信息。
3. [roles]：定义角色的权限等信息。
```ini
# 配置文件的语法
# =可以表示创建一个某个类的对象，也可以表示给对象的某个元素赋值，使用依赖注入时在对象前面加$

[main]
myRealm=cn.sxt.realm.MyRealm
# securityManager是整个shiro的核心，配置文件中的对象最后赋值给securityManager对象的属性才能生效。
securityManager.realm=$myRealm

[users]
mh=123
zhangsan=1234,role1,role2

[roles]
role1=user:add,user:delete
```

# 使用JdbcRealm完成身份认证
通过观察JdbcRealm的源码可知，要实现JdbcRealm：
1. 需要为jdbcRealm指定dataSource。
2. 在指定的dataSource所对应的数据库中需要有用户表users，该表中有username、password、password_salt等字段。

实现步骤：
1. 新建数据库。数据库中新建users表，表中有username、password、password_salt等属性。
2. 新建项目，导包，除了shiro需要的包外还需要额外导入这几个包。
    ```
    c3p0-0.9.5.2.jar
    mchange-commons-java-0.2.12.jar
    mysql-connector-java-8.0.11.jar
    ```
3. 新建ini配置文件。
    ```ini
    [main]
    # 新建一个数据源对象dataSource，并设置连接需要的四个属性
    dataSource= com.mchange.v2.c3p0.ComboPooledDataSource
    dataSource.driverClass= com.mysql.cj.jdbc.Driver
    dataSource.jdbcUrl=jdbc:mysql://localhost:3306/shiro?userSSL=false&serverTimezone=GMT%2B8
    dataSource.user=root
    dataSource.password=123456
    # 创建一个JdbcRealm对象，并把连接池对象赋值给jdbcRealm对象的dataSource属性
    jdbcRealm= org.apache.shiro.realm.jdbc.JdbcRealm
    jdbcRealm.dataSource=$dataSource
    # 最后把jdbcRealm对象赋值给securityManager的realm属性
    securityManager.realm=$jdbcRealm
    ```
4. 编码测试，代码与用户认证处相同。

# 认证策略
如果有多个Realm时，需要配置认证策略。在shiro中，有三种认证策略，默认策略是AtLeastOneSuccessfulStrategy。
1. AtLeastOneSuccessfulStrategy：如果一个或多个验证成功，则认证通过；如果没有一个验证成功，认证不通过。
2. FirstSuccessfulStrategy：如果有一个成功，则返回验证成功的Realm的信息，其他的Realm将不会被验证。
3. AllSuccessfulStrategy：只有所有的都成功才验证通过。

配置认证策略：
```
# 新建一个认证策略对象
authenticationStrategy=org.apache.shiro.authc.pam.AllSuccessfulStrategy
# 把认证策略对象赋值给securityManager对象的authenticator对象属性的authenticationStrategy属性。
# 该处不能新建一个authenticator对象再进行赋值
securityManager.authenticator.authenticationStrategy=$authenticationStrategy
```