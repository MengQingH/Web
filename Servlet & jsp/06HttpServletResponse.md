Web服务器收到客户端的http请求，会针对每一次请求，分别创建一个用于代表请求的request对象和代表相应的额response对象。
两个对象分别代表请求和响应，所以要获取客户端提交的数据，只需要找request对象，如果要向客户端输出数据，就找response对象

# response对象常用函数：
    setStatus(int sc)：设置响应状态码
    setDateHeader(String name,long l)：设置值为数值类型的消息头
    setHeader(String name,String value)：设置响应消息头
    getWriter()：获取PrintWriter对象回送数据（回送字符）
    getOutputStream()：获取输出流回送数据（回送字节）
    sendRedirect()：实现请求重定向，可以带数据给下一个界面(一个web资源收到客户端请求后，通知客户端去访问另一个web资源)

## getWriter()和getOutputStream()：
    向PrintWriter和OutputStream对象中写入的数据会被当做是响应消息的正文和响应状态行和响应头组合后输出到客户端
    区别：
    getWriter() 用于向客户机回送字符数据
    getOutputStream() 返回的对象，可以回送字符数据，也可以回送字节数据(二进制数据)
    注：同一个response对象中两个流不能同时使用，最后服务器会自动关闭流
## sendRedirect()方法：
    1.可以跳转到另一个界面：
    response.sendRedirect("/UserManager/MainFrame");
    2.可以向写一个界面传递数据，在url后面添加变量和值，同时用?隔开，多个量之间用&。注：传递中文有乱码，需要处理，且只能传递字符串(传递对象用session)
    response.sendRedirect("/UserManager/MainFrame?name1=value1&name2=value2");





    