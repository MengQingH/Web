# serclet多次映射
    客户端通过URL地址访问web服务器中的资源，所以servlet程序要被外界访问，必须把程序映射到url地址上，在web.xml文件中完成(03中servlet的部署)
    1. servlet可以多次映射
    2. 当映射一个servlet的时候可以多层，且
        <url-pattern>/servlet/index.html</url-pattern>文件类型不一定是后缀的类型
    3. servlet映射到url中也可以使用*通配符，但只能有两种固定的格式：
        一种是*.扩展名，*代表任意字符
            <url-pattern>*.do</url-pattern>任意以.do后缀结尾的url都能被匹配
        一种是以“/”开头并以“/*”结尾
            <url-pattern>/*</url-pattern>任意字符都能被匹配
            <url-pattern>/abc/*</url-pattern>类型为/abc/+任意字符的url都能被匹配
        匹配的优先级问题：
            谁的匹配度最高，谁就被选择
            *.do的优先级最低


# servlet单例问题
    servlet的整个生命周期内，init方法只会被调用一次。而每一次对这个servlet的访问请求都会使servlet引擎调用一次service方法，每一次访问请求中service引擎会创建一个新的HttpServletRequest对象和一个新的HttpServletResponse对象，然后将这两个对象作为参数传递给service方法，service方法再根据请求方式分别调用doXXX方法。
    因为service是单例，因此会出现线程安全问题，原则：
        1. 如果一个变量需要多个用户共享，则应当在访问变量的时候加同步机制
            synchronized(对象){
                //同步代码
            }
        2. 如果一个变量不需要共享，则直接在doGet()或者doPost()定义，这样不会存在线程安全问题


# load-on-startup标签配置
    通过配置<load-on-startup>可以指定某个servlet自动创建，可以完成网站启动时的初始化数据，网站中的定时完成的任务
    在xml文件<servlet>标签中配置<load-on-startup>标签(idea中可以使用注解配置)，标签中放一个数字表示该servlet被启动的顺序：
        <load-on-startup>1</load-on-startup>


# ServletConfig对象
    在Servlet的配置文件中，可以使用一个或多个<init-param>标签为servlet配置一些初始化参数。当servlet配置了初始化参数后，web容器会在创建Servlet对象时把这些参数封装到ServletConfig对象中，并在调用servlet的init方法时，将ServletConfig对象传递给servlet。进而通过ServletConfig对象可以得到当前servlet的初始化参数信息。
    1. 在Servlet中，可以通过getServletConfig对象获取ServletConfig对象，再调用ServletConfig对象的getInitParameter()方法传入定义的参数名获取参数的值
    2. 获取所有参数：通过ServletConfig的getInitParanmeterNames方法获取所有的参数名，保存在一个Enumeration中，再通过循环获取参数值
        ```java
        Enumeration<String> names = this.getServletConfig().getInitParameterNames();
                while (names.hasMoreElements()){
                    String name = names.nextElement();
                    System.out.println(this.getServletConfig().getInitParameter(name));
                }
        ```

