# HTTP超文本传输协议：
    1. 工作在tcp/ip协议的基础上
    2. 1.0、1.1两个版本，目前通用的是1.1版本
        HTTP1.0为短连接，发送完数据断开连接
        HTTP1.1为长连接，发送完数据不断开链接


# HTTP请求：
    客户端链接服务器后向服务器请求某个web资源，称之为客户端向服务器发送了一个HTTP请求。一个完整的HTTP请求包含如下内容：
        一个请求行:用于描述客户端的请求方式，请求的资源名以及使用的HTTP版本号
        若干消息头：用于描述客户端请求哪台主机以及客户端的一些环境信息等，并不是每一次消息头都相同
        实体内容(消息头和实体内容之间用空行隔开)

    HTTP请求举例：
        GET /test/hello.html HTTP/1.1                   请求行
        Accept: */*                                     消息头(消息名：内容)
        Referer: http://localhost:8080/test/abc.html  
        Accept-Language: zh-cn
        User-Agent: Mozilla/4.0 
        Accept-Encoding: gzip, deflate  
        Host: http://localhost:8080
        Connection: Keep-Alive

        请求的数据(格式：内容名字=内容体)

## HTTP请求的请求行：
    格式：请求方式 请求的资源名 HTTP版本号
    请求方式：POST,GET,HEAD,OPTIONS,DELETE,TRACE,PUT
    常用的有：POST,GET
        GET提交：请求的数据会附在URL之后，就是把数据放置在HTTP头协议中，以？来分割URL和传输数据，多个参数用&连接，例如：login.action?name=123&password=123；因此GET提交的数据会在地址栏中显示出来
        POST提交：把提交的数据放置在HTTP包的包体中，即实体内容中
    get提交和post的提交的区别
        从安全看 get<post,因为get会把提交的信息显示到地址栏
        从提交内容看 get<post,get 一般不要大于2k, post理论上无限制，但是在实际开发中，建议不要大于64k
        从速度看,get>post
        Get可以保留uri中的参数，利于收藏

## HTTP请求的消息头：
    (浏览器发给服务器的信息，所以以下信息为浏览器的信息)
    Accept: text/html,image/*   (浏览器可以接收的数据类型)
    Accept-Charset: ISO-8859-1  (接受的字符编码类型)
    Accept-Encoding: gzip,compress (可以接受gzip,compress压缩后数据)
    Accept-Language: en-us,zh-cn   (浏览器支持中，英文)
    Host: www.sohu.com:80           (请求的主机)
    If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT  (告诉服务器，缓冲中有这个资源文件，该文件的时间是···)
    Referer: http://www.sohu.com/index.jsp  (告诉服务器，我来自哪里,该消息头，常用于防止盗链)
    User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)(浏览器的内核)
    Cookie:浏览器中保存的cookie
    Connection: close/Keep-Alive   (保持连接，发完数据后，不关闭连接)
    Date: Tue, 11 Jul 2000 18:23:51 GMT (浏览器发送该http请求的时间)
可以使用request对象的getHeader方法获取消息头的内容



# HTTP响应
    HTTP响应代表服务器向客户端回送的数据
    结构：
        状态行：用于描述服务器对请求的处理结果
        多个消息头：用于描述服务器的基本信息，以及数据的描述，服务器通过这些数据的描述信息，可以通知客户端如何处理这些数据
        实体内容(消息头与实体之间用一个空行隔开)
    举例：
        HTTP/1.1 200 OK                     状态行
        Server: Microsoft-IIS/5.0           多个消息头
        Date: Thu, 13 Jul 2000 05:46:53 GMT
        Cotent-Length: 2291
        Content_Type: text/xml
        Cache-control: private

        <HTML>                              实体内容
        <BODY>
        ......
## HTTP响应的状态行：
    格式：HTTP版本号 状态码 原因叙述
    举例：HTTP/1.1 200 OK  
    状态码及含义：状态码用于表示服务器对请求的处理结果，它是一个三位的十进制数
        100-199：表示接受请求成功，要求客户端继续提交写一次请求才能完成整个处理过程
        200-299：表示成功接收请求并完成整个处理过程，常用200
        300-399：为完成请求，客户端需要进一步细化请求，例如请求的资源已经移到一个新地址，常用302、307
        400-499:客户端的请求有错误，常用404：找不到资源
        500-599：服务端出现错误，常用500：服务器端代码有错误
## HTTP响应的消息头：
    Location: http://www.baidu.org/index.jsp  (让浏览器重新定位到url)
    Server:apache tomcat    (服务器的类型)
    Content-Encoding: gzip  (回送资源的压缩方式)
    Content-Length: 80      (回送的数据大小)
    Content-Language: zh-cn (文本的语言类型)
    Content-Type: text/html; charset=GB2312 (内容格式; 编码方式)
    Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT (浏览器请求的资源上次更新时间)
    Refresh: 1;url=http://www.baidu.com (过一段时间转到该url)
    Content-Disposition: attachment; filename=aaa.zip (有文件下载)
    Transfer-Encoding: chunked  (传输的编码)
    Set-Cookie:SS=Q0=5Lb_nQ; path=/search(cookie详解)
    Expires: -1(如何缓存页面 针对IE)
    Cache-Control: no-cache  (如何缓存页面 针对火狐)
    Pragma: no-cache   (如何缓存页面 针对其他浏览器)
    Connection: close(Keep-Alive)   (发送完数据，保持还是关闭连接)
    Date: Tue, 11 Jul 2000 18:23:51 GMT
文件下载举例：
```java
    response.setHeader("Content-Disposition", "attachment;filename=a.txt");
    //1 获取文件全路径
    String path = "D:/a.txt";
    //2 创建文件输入流
    FileInputStream fis = new FileInputStream(path);
    byte b[] = new byte[1024];
    int len = 0;

    OutputStream os = response.getOutputStream();
    while ((len = fis.read()) != -1) {
        os.write(b, 0, len);
    }
    os.close();
    fis.close();
```
缓存页面举例：

    浏览器缓存机制：在访问一个界面时，浏览器会默认缓存页面，放在缓存文件夹中，如果下一次从地址栏重新进入该页面，浏览器会从缓存中获取这个页面。如果服务器中的这个页面发生变化，则浏览器显示的还是缓存时的界面(若点击刷新则浏览器会重新向服务器发送请求，不会发生该问题)
1. 某些及时性很高的页面可以设置为不缓存页面：
```java
    //设置不缓存页面IE
    response.setDateHeader("Expires",-1);
    //为保证兼容性，其他浏览器也设置不缓存
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
```
2. 某些页面需要缓存固定时间
```java
response.setDateHeader("Expires", System.currentTimeMillis()+3600*1000*24);
//参数传入一个固定的时间点，到该时间点后，浏览器停止缓存该页面
```


