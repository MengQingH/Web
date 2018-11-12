## context-param
该元素用来声明整个应用范围（整个web项目）内的上下文参数。上下文（ServletContext对象）。
1. 子标签：
    * param-name：用来设置参数的名称
    * param-value：用来设置参数的值
    ```xml
    <context-param>
        <param-name></param-name>
        <param-value></param-value>
    </context-param>
    ```
2. 初始化过程
    1. 启动web项目时，容器会读取web.xml文件中的两个节点listener和context-param
    
    2. 接着容器会创建一个ServletContext（上下文），整个web项目内都能使用这个上下文
    3. 然后容器会把读取到的context-param转化为键值对，并交给ServletContext
    4. 容器创建listener标签中的类实例，即创建监听（listener中的类可以自己定义但必须继承ServletContextListener）
    5. 在监听的类中会有一个contextInitialized(ServletContextEvent event)初始化方法，在这个方法中可以通过event.getServletContext().getInitParameter("contextConfigLocation") 来得到context-param 设定的值。在这个类中还必须有一个contextDestroyed(ServletContextEvent event) 销毁方法.用于关闭应用前释放资源，比如说数据库连接的关闭。
    6. 得到ServletContext中的值后可以进行一些操作
3. 也可以在servlet和jsp文件中获取这些参数。