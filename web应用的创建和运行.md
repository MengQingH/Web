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


## 解决idea中jsp发布后才能修改的问题
看下条


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


## 解决web应用运行时找不到jdbc驱动的问题
    详情：ClassNotFound:com.mysql.jdbc.Driver
    解决方法：在Java项目中，只需要导入驱动包就可以进行数据库的连接。但是在web应用中由于web应用运行在Tomcat容器中，所以只在IDE中加载驱动服务器找不到该驱动。把驱动放入Tomcat的lib文件夹下即可解决该问题。

