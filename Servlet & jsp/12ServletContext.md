# ServletContext
属于域对象，类似一个公用的空间，所有的用户都可以访问。

* web容器启动时，会为每一个web应用创建一个ServletContext对象，代表当前web应<!>
* web应用启动时自动创建，web应用关闭时或服务器关闭时销毁
* 由于一个web应用的所有Servlet对象共享同一个ServletContext对象，因此可以通过该对象实现通信
* 应用
    1. 通过ServletContext对象实现数据共享
    2. 获取web应用的初始化参数(所有servlet共享的参数)
    3. 实现servlet的转发
    4. 利用ServletContext对象读取资源文件(.properties文件)

## 常用方法：
* this.getServletContext()：获取引用
* ServletConfig.getServletContext()：获取引用

* setAttribute(String name,Object value)：向ServletContext中放入属性
* getAttribute(String name)：取出属性值
* getAttributeNames()：获取所有的属性名放在一个Enumeration中
* removeAttribute(String name)：删除属性

* getParameter(String name)：获取配置参数
* getParameterNames()：获取所有的配置参数名



## 实现数据通信：
放入属性：
```java
//获取ServletConfig对象的引用
javax.servlet.ServletContext servletContext = this.getServletContext();
//设置属性
servletContext.setAttribute("name","mh");
```
取出属性：
```java
//取出ServletContext对象中的某个属性
ServletContext servletContext = this.getServletContext();
//获取单个属性
String name = (String) servletContext.getAttribute("name");
//获取多个
Enumeration e = servletContext.getAttributeNames();
while (e.hasMoreElements()){
    //取出属性值
}
```


## 获取web应用的初始化参数
获取参数有获取单个参数getParameter()和获取所有参数名getParameterNames()再通过遍历Enumeration取值两个方法

### 1、获取一个servlet独有的参数信息
配置一个servlet单独享有的配置信息：
* 在web.xml文件中的该Servlet节点下配置
* 可以通过注解@WebServlet中initParam属性配置
```java
//获取该servlet单独的配置信息
ServletConfig servletConfig = this.getServletConfig();
//1 获取一个单独的参数
s = servletConfig.getInitParameter("name");
//2 获取所有的参数值
Enumeration e = servletConfig.getInitParameterNames();
while (e.hasMoreElements()){
    //获取参数值
}
out.println(s);
```

### 2、获取所有servlet共享的参数信息
配置方法：在web.xml文件中配置以下信息
```xml
<context-param>
    <param-name>name</param-name>
    <param-value>value1</param-value>
</context-param>
```
获取方法：
```java
//获取该web应用下所有的servlet共享的配置信息
ServletContext servletContext = this.getServletContext();
//获取一个参数
String s = servletContext.getInitParameter("name");
//
Enumeration paraEnum = servletContext.getInitParameterNames();
out.println(s);
```

## 实现servlet的转发：
```java
//与request的请求转发相同
this.getServletContext().getRequestDispatcher("/资源名").forward(request,response);
```

## 读取资源文件：
```java
ServletContext servletContext = this.getServletContext();
//读取文件并返回一个流
InputStream is = servletContext.getResourceAsStream("FileName");
//读取文件的绝对路径(从当前servlet在tomcat的存放文件夹开始算起的相对路径)
//如果文件放在src目录下，则使用类加载器
String path = servletContext.getRealPath("FileName");
```
