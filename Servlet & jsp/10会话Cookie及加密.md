# 会话

## 会话概念
    用户打开浏览器访问一个网站，点击多个超链接，访问服务器多个web资源，然后关闭浏览器，整个过程称为一个会话。
    打开浏览器到关闭浏览器之间的过程称为一次会话，不管访问了多少网站

# cookie
    cookie时是客户端技术，服务器把每个用户的数据以cookie的形式写给各自的浏览器(即服务端保存在客户端的信息)。当用户使用浏览器再去访问服务器中的web资源时，就会带着各自的数据，这样web资源处理的数据就是用户各自的数据了。
* cookie中的也是以键值对的形式存在的，二者类型都是String
* 在服务器端创建，保存在客户端，同一个web应用的多个cookie会放在同一个文件中
* 生命周期可以自行设置，不设置生命周期，则浏览器关闭时就消亡。
* cookie可以被多个浏览器共享
* 如果重名就会替换原有的值

用途：
1. 保存上次登陆时间等信息
2. 保存用户名和密码
3. 用户访问网站的喜好
4. 网站的个性化内容，如定制网站的服务和内容

## 相关函数
* public Cookie(String name,String value)：创建cookie
* setValue()：设置值
* setMaxAge()：设置生命周期，设置为0时删除该cookie；默认为-1，关闭浏览器时清除cookie
* getName() getValue()：获取cookie的名和值
* response.addCookie(cookie)：把cookie添加到response中
* request.getCookies()：获取http请求中的所有cookie

## 创建cookie
```java
    //创建cookie
    Cookie cookie = new Cookie("name","mh");
    //设置cookie的生命周期
    cookie.setMaxAge(3600);
    //把cookie信息放在response中回写给浏览器
    response.addCookie(cookie);
    //浏览器获取到http响应的信息，保存该cookie
```
## 读取cookie
当浏览器中有cookie时，会把该web应用的cookie信息放在http请求中，servlet中就可以获取这些cookie
```java
    //读取cookie，该函数获取所有的cookie对象，放入一个数组中
    Cookie[] cookies = request.getCookies();
    //获取cookie中的信息
    for (Cookie c:
            cookies) {
        out.print(c.getName()+"="+c.getValue());
    }

    //由于cookie信息存放在http请求的消息头中，所以直接获取消息头的内容也可以获取到cookie的信息
    String s = request.getHeader("cookie");
    out.println("<br/>"+s);
```

## cookie加密
    cookie在客户端存放时是以明文方式存储的，因此安全性较低，所以可以通过加密算法加密保存cookie信息
    md5加密算法：把字符串通过该算法加密后产生32位密文(稳定，对某一个字符串多次使用产生的密文相同)，该过程不可逆(?)。登录验证密码时，对用户输入的密码进行加密，然后与数据库进行比对，看是否符合。
```java
import java.security.MessageDigest;

public class TestMD5 {
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args){
        
        String MD5_String = MD5("111");
        System.out.println(MD5_String);
    }
}
```