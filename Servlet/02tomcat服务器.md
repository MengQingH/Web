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


# 浏览器访问一个web站点http://localhost:8080/MyWeb/hello.html的流程：
    1 浏览器解析主机名localhost
        ① 查询本地文件hosts中是否有该主机名的ip
        ② 若①找不到，就用电脑默认的DNS服务器上查找该主机的ip
    2 浏览器尝试连接web服务器
    3 浏览器发出http请求
    4 服务器解析主机
    5 解析web应用
    6 解析资源名(web.xml中的资源部署部分)
    7 查询到资源位置
# 配置域名方法(只在本机中访问有效)：
    (1) 在C:\WINDOWS\system32\drivers\etc 下的host文件添加 (ip 想要的域名)
    (2) 在tomcat 的server.xml文件添加主机名 
        <Host name="域名" appBase="web应用路径”>
                <Context path="/" docBase="路径" />
        </Host>
    (3) 配置首页面，如果连端口都不希望带，则可以吧tomcat的启动端口设为80即可.
    (4) 重启生效


# tomcat体系图：
tomcat(server服务器)
    |-service(服务)
        |-connector(连接器1，可以通过不同的协议连接服务器)
        |-connector(连接器2)
        |-engine(引擎)
            |-host1(主机，引擎中可以有多个主机)
            |    |-context1(web应用，主机中可以有多个web应用)
            |    |-context2(web应用)
            |-host2(主机)
        

