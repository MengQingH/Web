# idea中servlet的运行
1. new project选择Web Application，并配置好服务器(Application server)
2. 输入项目名，finished
3. 在WEB-INF目录下新建classes(存放编译后的class文件)和lib(存放第三方包)文件夹
4. 配置classes文件夹路径 File -> 选择 Project Structure -> 选择 Module -> 选择Paths -> 选择 “Use module compile output path” -> 将Output path和Test output path都选择刚刚创建的classes文件夹。
5. 配置lib文件夹路径 点击Paths旁边的Dependencies 点击右边的”+”号 -> 选择”Jars or Directories” -> 选择刚刚创建的lib文件夹 -> 然后一路OK就行了
6. 在Project Structure中选择Artifacts -> 选择+号 -> 选择Web Application:Exploded -> 选择from Modules -> 选择该项目
7. 点击Run -> 选择 Edit Configurations -> 击“+”号 -> 选择“Tomcat Server” -> 选择“Local” -> 输入Name -> 点击configurations选择服务器路径 -> 点击deployment -> 点击+号 -> 选择Artifacts -> 右边application context中输入项目名 -> 点ok


# 配置Servlet
    方法1：通过web.xml配置
    方法2：通过注解WebServlet配置servlet，常用属性：
* name:String，指定Servlet 的 name 属性，等价于 <servlet-name>。如果没有显式指定，则该 Servlet 的取值即为类的全限定名。
* urlPatterns:String[],指定一组 Servlet 的 URL 匹配模式。等价于<url-pattern>标签:@WebServlet(urlPatterns = {"/servlet"})
* loadOnStartup:int,指定 Servlet 的加载顺序，等价于 <load-on-startup>标签。
* initParams：WebInitParam[],指定一组 Servlet 初始化参数，该数组的元素为@WebInitParam(name = "", value = "")的形式，等价于<init-param>标签。
* asyncSupported:boolean,声明 Servlet 是否支持异步操作模式，等价于<async-supported> 标签。
* description:String,该 Servlet 的描述信息，等价于 <description>标签。
* displayName:String,该 Servlet 的显示名，通常配合工具使用，等价于 <display-name>标签。
```java
    @WebServlet(name = "t1",urlPatterns = {"/servlet"})
    @WebServlet(initParams = { @WebInitParam(name = "Site :", value = "http://roseindia.net"),@WebInitParam(name = "Rose", value = "India", description = "detail-info") })
```


#tomcat服务器中的运行
1. tomcat目录webapps文件夹下新建一个web应用
2. 在应用目录下新建一个WEB-INF目录，目录中新建classes和lib文件夹
3. classes文件夹中新建一个servlet，并编译
4. 在web.xml中部署该servlet
5. tomcat中打开该程序
logs目录下可以查看运行日志与错误信息等


