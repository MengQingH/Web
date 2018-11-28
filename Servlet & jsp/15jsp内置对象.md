## 内置对象：
jsp中可以使用内置对象的原因是因为在把jsp文件编译成class时，在前面创建了这些局部变量

1. out：PrintWriter对象，向客户端输出数据

2. request：封装了所有的请求信息，相当于Servlet中的HttpServletRequest对象

3. response：封装了所有的响应信息，相当于Servlet中的HTTPServletResponse对象

4. session：封装了所有的会话信息，相当于Servlet中的HTTPSession

5. application：相当于Servlet中的ServletContext

6. pageContext：jsp的域对象，可以设置取出属性，只在本页面生效

7. exception：代表运行时的一个异常

8. page：代表这个实例本身(使用比较少)

9. config：代表jsp对应的Servlet的配置，相当于ServletConfig

## 四大作用域：
1. page：在当前页面不会实例化
2. request：在一次请求中为同一个对象，下次请求会重新实例化一个对象
3. session：一次会话，只要客户端cookie传递的Jsessionid不变，session不会重新实例化，不超过默认时间。实际有效时间：
    * 浏览器关闭，cookie失效
    * 默认时间，在时间范围内无任何交互，在tomcat的web.xml文件中配置
4. application：tomcat启动项目时菜单实例化，关闭时销毁