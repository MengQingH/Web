## 创建servlet项目
1. new project选择Web Application，并配置好服务器(Application server)
2. 输入项目名，finished
3. 在WEB-INF目录下新建classes(存放编译后的class文件)和lib(存放第三方包)文件夹
4. 配置classes文件夹路径 File -> 选择 Project Structure -> 选择 Module -> 选择Paths -> 选择 “Use module compile output path” -> 将Output path和Test output path都选择刚刚创建的classes文件夹。
5. 配置lib文件夹路径 点击Paths旁边的Dependencies 点击右边的”+”号 -> 选择”Jars or Directories” -> 选择刚刚创建的lib文件夹 -> 然后一路OK就行了
6. 在Project Structure中选择Artifacts -> 选择+号 -> 选择Web Application:Exploded -> 选择from Modules -> 选择该项目
7. 点击Run -> 选择 Edit Configurations -> 击“+”号 -> 选择“Tomcat Server” -> 选择“Local” -> 输入Name -> 点击configurations选择服务器路径 -> 点击deployment -> 点击+号 -> 选择Artifacts -> 右边application context中输入项目名 -> 点ok


## 配置Servlet
    方法1：通过web.xml配置
    方法2：通过注解WebServlet配置servlet，常用属性：
* name：String，指定Servlet 的 name 属性，等价于 <servlet-name>。如果没有显式指定，则该 Servlet 的取值即为类的全限定名。
* urlPatterns：String[],指定一组 Servlet 的 URL 匹配模式。等价于<url-pattern>标签：@WebServlet(urlPatterns = {"/servlet"})
* loadOnStartup：int,指定 Servlet 的加载顺序，等价于 <load-on-startup>标签。
* initParams：WebInitParam[],指定一组 Servlet 初始化参数，该数组的元素为@WebInitParam(name = "", value = "")的形式，等价于<init-param>标签。
* asyncSupported：boolean,声明 Servlet 是否支持异步操作模式，等价于<async-supported> 标签。
* description：String,该 Servlet 的描述信息，等价于 <description>标签。
* displayName：String,该 Servlet 的显示名，通常配合工具使用，等价于 <display-name>标签。
```java
    @WebServlet(name = "t1",urlPatterns = {"/servlet"})
    @WebServlet(initParams = { @WebInitParam(name = "Site :", value = "http://roseindia.net"),@WebInitParam(name = "Rose", value = "India", description = "detail-info") })
```


## on update action和on frame deactivation
### on update action
意思是当手动触发update时，做什么，即当手动点击update按钮时IDE做什么。
* Update resourses：更新静态的资源如html,css,js等，运行和调试模式下都立即生效
* Update classes and resourses：更新Java，jsp和静态资源。Java调试模式下立即生效，运行模式下需要Redeploy才能生效；jsp运行和调试下都立即生效
* Redeploy：重新部署项目并发布到Tomcat中，相当于把原来的删掉然后重新发布，更改servlet需要重新发布后才能实现修改
* Restart server：重启Tomcat

### on frame deactivation
当IDE失去焦点时，做什么。最好设置为do nothing
* Do nothing：什么都不做
* Update resourses：更新静态的资源如html,css,js等，运行和调试模式下都立即生效
* Update classes and resourses：更新Java，jsp和静态资源。Java调试模式下立即生效，运行模式下需要Redeploy才能生效；jsp运行和调试下都立即生效


## 使用myBatis
1. 把导入myBatis的jar包导入
2. 在src下新建全局配置文件（没有名称和地址的要求）
    ```xml
    <!-- 在全局配置中引入DTD或schema -->
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <!--声明环境的集合。default引用的是environment的id，表示当前使用的是该环境-->
        <environments default="default">
            <!--声明可能使用的环境，内部是数据库相关内容-->
            <environment id="default">
                <!--设置事务的方式-->
                <transactionManager type="JDBC"></transactionManager>
                <!--数据库连接池-->
                <dataSource type="POOLED">
                    <!--jdbc的变量配置-->
                    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/ssm"/>
                    <property name="username" value="root"/>
                    <property name="password" value="123456"/>
                </dataSource>
            </environment>
        </environments>
        <mappers>
            <mapper resource="mapper/FlowerMapper.xml"/>
        </mappers>
    </configuration>
    ```
3. 在src目录下新建一个以mapper结尾的包，在包下新建：实体类名+Mapper.xml文件。作用：编写需要执行的sql命令，相当于实现类。
    ```xml
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <!--namespace:实现类的全路径(包名+类名)-->
    <mapper namespace="F">
        <!--id：方法名
        parameterType：定义参数类型
        resultType：返回值类型，如果返回值是List，则需要写明List的泛型，因为myBatis是对jdbc进行封装，一行一行读取数据
        -->
        <select id="selAll" parameterType="">
            select
        </select>
    </mapper>
    ```
4. 测试结果
