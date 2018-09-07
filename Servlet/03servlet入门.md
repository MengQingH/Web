# servlet介绍
servlet，java编写的服务器程序
    由服务器端调用和执行
    实质是Java程序(Java类)


# 三种开发方式：
    1 实现Servlet接口
    2 继承GenericServlet
    3 继承HttpServlet(常用，指能够处理http请求的servlet该类在实现接口时重写了service方法，该方法体内的代码会自动判断用户的请求方式，如为get请求，则调用doGet方法，如为post请求，则调用doPost方法)
实例：用实现Servlet接口的方式开发一个Servlet
    WEB-INF目录下建立classes和lib文件夹，并配置
    建立servlet文件，编写代码
    在web.xml文件中部署servlet：根据serlvet规范，需要将Servlet部署到web.xml文件,该部署配置可以从examples下拷贝
        ```xml
        <servlet>   <!-- 用于注册servlet元素 -->
        <!--servlet-name Servlet注册名, 该名字可以任意定义，默认就使用该Servlet的名字-->
        <servlet-name>MyFirstServlet</servlet-name>三
        <!--servlet-class servlet全路径，要指明该Servlet的包名+类名 -->
        <servlet-class>com.hsp.MyFirstServlet</servlet-class> ④
        </servlet>
        ```
        <servlet-mapping>   <!--Servlet的映射-->
            <!--Servlet-name注册名，要和上面的注册名一样-->
            <servlet-name>MyFirstServlet</servlet-name>②
            <!--url-pattern url中访问该Servlet的资源名-->
            <url-pattern>/ABC</url-pattern>①
        </servlet-mapping>
        <!-- 浏览器中调用流程http://localhost:8088/ABC ①②③④ 浏览器通过url中的资源名找到对应的服务器中的资源名称，再通过这个名称找到资源的位置>
        ```
使用HttpServlet的方式开发servlet：
```java
    public class MyHttpServlet extends HttpServlet{

        //在HttpServlet 中，设计者对post 提交和 get提交分别处理
        //<form action="提交给?" method="post|get"/>,默认是get
        protected void doGet(HttpServletRequest req,HttpServletResponse resp)
                throws ServletException,java.io.IOException{
            resp.getWriter().println("i am httpServet doGet()");
        }
        protected void doPost(HttpServletRequest req,HttpServletResponse resp)
                throws ServletException,java.io.IOException{ 
            resp.getWriter().println("i am httpServet doPost() post name="+req.getParameter("username"));
        }
    }
```
# get提交和post的提交的区别
    从安全看 get<post,因为get会把提交的信息显示到地址栏
    从提交内容看 get<post get 一般不要大于2k, post理论上无限制，但是在实际开发中，建议不要大于64k
    从速度看 get>post
    Get可以保留uri中的参数，利于收藏


# servlet工作时序图：
    1 浏览器解析主机名
        ① 查询本地文件hosts中是否有该主机名的ip
        ② 若①找不到，就用电脑默认的DNS服务器上查找该主机的ip
    2 浏览器尝试连接web服务器
    3 浏览器向服务器发出http请求
    4 服务器解析主机
    5 解析web应用
    6 解析资源名(web.xml中的资源部署部分)
    7 查询到资源位置
    8 服务器使用反射机制创建实例调用init方法把该实例装载到内存(只调用一次)
    9 web服务器把接收到的http请求封装成Request对象作为service函数的参数传进去，调用service函数(每调用一次servlet service函数就会被调用一次)
    10 获取response中的信息，形成http相应格式
# servlet生命周期：
    当serlvet第一次被调用的时候，会触发init函数，该函数会把servlet实例装载到内存(init函数只会被调用一次)
    然后去调用servlet的service 函数
    当第二次后访问该servlet 就直接调用 service 函数.
    当 web应用 reload 或者 关闭 tomcat 或者 关机 都会去调用destroy函数，该函数就会去销毁serlvet
生命全过程：
    1.加载
    2.实例化
    3.初始化
    4.处理请求
    5.退出服务


