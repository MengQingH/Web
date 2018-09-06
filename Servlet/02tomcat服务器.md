# tomcat服务器：
目录：
    bin: 启动和关闭tomcat的bat文件
    conf: 配置文件 
        -->server.xml : 该文件用于配置和 server 相关的信息, 比如tomcat启动端口号,配置Host,配置Context 即web应用 
        -->web.xml : 该文件配置与 web应用(web应用就相当于是一个 web站点)
        -->tomcat-users.xml: 该文件用户配置tomcat 的用户密码 和 权限
    lib 目录: 该目录放置运行tomcat 运行需要的jar包
    logs 目录：存放日志, 当我们需要去查看日志的时候，很有用!,当我们启动tomcat错误时候，可以查询信息.
    webapps 目录: 该目录下，放置我们的web应用(web 站点), 比如:
        建立  web1 目录  下面放置我们的html 文件 jsp 文件..图片... 则 web1就被当做一个web应用管理起来(☞ 特别说明tomcat 6.0 以后支持)
    work: 工作目录: 该目录用于存放jsp被访问后 生成的对应的 server文件和.class文件


# 访问web应用的某个文件：
例：http://localhost:8080/web1/hello.html (url:统一资源定位)(uri:web1/hello.html,为url的一部分)
    http:协议
    localhost:域名
    8080:端口
    web1:web应用
    hello.html:资源文件


# web应用：
也成为web应用程序，是多个web资源的集合。可以理解为硬盘上的一个目录，用于管理多个web资源，如html文件、css文件、js文件、动态web页面等
## web应用目录：开发web应用时，应按照特定的目录结构来存放这些文件，否则可能会无法访问该应用
                      |-web.xml(配置当前web应用的信息)、taglib.tld  
            |-WEB-INF-|-lib目录：web应用需要的各种jar文件
            |         |-classes目录：类的包目录，各种class文件
web应用目录--
            |
            |-html文件、jsp文件
## 虚拟目录管理：管理除webapps目录外其他的目录中的web应用
配置虚拟目录在tomcat的conf目录下的server.xml的<Host>节点间添加<Context>节点：<Context path="" docBase="">
    path:访问时输入的web应用名，即在url中访问时的名称
    docBase:web应用的绝对目录
其他属性：
reloadable:如果设为ture ，表示 tomcat 会自动更新 web应用，这个开销大，建议在开发过程中，可以设为true, 但是一旦真的发布了，则应当设为false;
upackWAR:如果设为 ture ，则自动解压，否则不自动解压.

