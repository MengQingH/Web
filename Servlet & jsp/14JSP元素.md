# 指令元素
用于从jsp发送一个信息到容器，比如设置全局变量，文字编码，引入包等。
1. page指令：<%@ page contentType="text/html;charset=UTF-8"  %>
    * 常用
    * language="":jsp中嵌入的代码
    * import="包.* 包.类名"：在该jsp中引入包或某个具体的类
    * contentType="":指定网页以什么方式显示界面
    * pageEncoding="":指定Servlet以什么方法翻译jsp，指定网页以什么方式显示界面

    * session=[true|false]:是否在jsp中获取session对象，默认为true
    * buffer=[8k|指定大小]:给out对象的缓冲区多大，默认8k
    * aotoFlash=[true|false]:当buffer满后是否自动刷新到浏览器，默认为true
    * isTreadSafe=[true|false]:默认为true表示该jsp的线程安全由程序员控制，false则表示对应的Servlet将实现安全接口
    * errorPage="相对该jsp的界面":当jsp页面出现错误的时候，自动跳转到指定的jsp界面，当以/开头时，表示当前web应用的根目录，不以/开头，表示相对本jsp页面
    * isErrorPage=[true|false]:指定本界面是否用于error界面，默认为false，如果设置为true，则可以直接在本界面使用exception内置对象
2. include指令：该指令用于引入一个jsp文件，jsp引擎会把两个jsp文件翻译成一个Servlet文件，因此也称静态引入。
    * 示例：<%@ inclue file="filename"%>
    * 在不同的地方引入jsp文件，其内容也显示在不同的地方
    * 被引入的界面只需要保留page指令即可，html、body等标签可省略。
    * 文件路径以/开头时，表示当前web应用的根目录
3. 允许在jsp页面使用自定义的标签
    * <myTag:xx 属性>

# 脚本元素
Java代码片段
1. 片段：<% java代码 %>。jsp文件翻译为.java文件后<% %>中的Java代码被放入一个叫jspServlet的方法中.
2. 表达式:<%=java表达式%>
3. declaration声明：
    * <%! 变量声明 %>：<%! int i = 10 %>。定义的变量为jsp对应的Servlet的成员变量
    * <%! 函数声明 %>：成员函数，不能在<% %>中定义函数
        <%! int add(){

        }%>

# 动作元素
* <jsp:forward file=""></jsp:forward>:跳转到别的页面
    web应用中的jsp文件都是放在WEB-INF目录下，因为直接放在web目录下用浏览器可以直接访问很不安全，而在该目录下的jsp文件无法用浏览器直接访问。因此需要在web目录下放一个web应用入口，里面可以写一个<jsp:forward>标签指向WEB-INF目录下的jsp页面。
* <jsp:forward inlcude page=""></jsp:include>:动态引入，与指令元素中的include引入不同的是引入后两个jsp页面都会翻译为.class文件
