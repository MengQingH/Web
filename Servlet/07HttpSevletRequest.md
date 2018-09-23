HttpServletRequest对象代表客户端的请求，当客户端通过Http协议访问服务器时，Http请求头中的所有数据都封装在这个对象中，通过调用这个对象的方法，可以获得这些信息

# 常用方法
    getRequestURL():返回客户端发出请求时的完整URL
    getRequestURI():返回客户端请求行中的资源部分
    getQueryString():返回请求行中的参数部分(参数名+值)，即?后面的资源部分
    getRemoteAddr():返回发出请求的客户端的IP地址
    getRemoteHost():返回发出请求的客户端的完整主机号
    getRemotePort():返回发出请求的客户端的网络端口号
    getLocalPort():返回web服务器使用的端口号
    getLocalAddr():返回web服务器使用的ip地址
    getLocalName():返回web服务器使用的主机名

    //获得请求头
    getHeader(String name):获取消息头的值
    getHeaderNames():获取全部的消息头名，放在一个Enumeration中
    
    //获得请求参数(客户端提交的数据)
    getParameter(String name):获取某个参数的值
    getParameterValues(String name):
    getParameterNames():获取全部的消息名，放在一个Enumeration中

## 实例
    获取表单中的内容
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = response.getWriter();

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String sex = request.getParameter("sex");
    //接收复选框内容，使用getParameterValues方法
    String[] hobby = request.getParameterValues("hobby");
    String city = request.getParameter("city");
    String hidden = request.getParameter("hidden");

    out.println("<p>用户名："+username+"</p>");
    out.println("<p>密  码："+password+"</p>");
    out.println("<p>性  别："+sex+"</p>");
    out.println("<p>爱  好：");
    if(hobby!=null)
    for (int i = 0; i < hobby.length; i++) {
        out.println(hobby[i]);
    }
    out.println("</p>");
    out.println("<p>城  市："+city+"</p>");
    out.println("<p>隐  藏："+hidden+"</p>");
}
```

# 实现请求转发
    请求转发是指一个web资源收到客户端的请求后，通知服务器(不返回浏览器，服务器中转发，所以不能访问该web应用以外的url)去调用另一个web资源进行处理。
    方法：Request对象提供了一个getRequestDispatcher()方法，该方法返回一个RequestDispatcher对象，调用这个对象的forward方法可以直线请求转发。
```java
//获取RequestDispatcher对象，并把request和response对象传递给下一个servlet
request.getRequestDispatcher("/Forward2").forward(request,response);//只能访问同一个web应用下的其他资源，不需要加web应用名
```
## 域对象
    request对象是一个域对象，可以向对象中储存数据，需要的时候获取。
    request对象通过setAttribute(String name,Object o)方法来储存一个键值对，需要的时候通过getAttribute(String name)方法传入键名获取值。request对象中的数据生命周期是一次请求，浏览器发出的请求在返回浏览器之前为一次请求
```java
//使用域对象在两个servlet之间传递数据
request.setAttribute("username","mh");

String s = request.getAttribute("username");
```
    
* 请求转发发生在服务器端，应用收到转发请求后直接在服务器中转向另一个应用，不经过浏览器
* 使用forward不能转发到该web应用之外的url
* 由于forward发生在服务器中，所以实现请求转发的两个servlet使用的是同一个request和response

## 请求重定向
一个web资源收到请求后，通知浏览器去访问另一个web资源，称之为请求重定向。
```java
response.setStatus(302);
response.setHeader("Location", "/servletPro/Servlet2");
// 上面两句话等价	response.sendRedirect("/servletPro/Servlet2");

```