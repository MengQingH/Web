# session域对象
    Session是服务端的技术，服务器在运行时，可以为每一个用户的浏览器创建一个其独享的session对象，该浏览器再次访问该web应用时，可以获取session中的信息。其他浏览器访问该应用则不能访问该session，但是可以创建另外一个session
* session为一个域对象，可以放入属性，其中属性名为String类型，值为Object类型
* 在一个web应用中，一个浏览器对应一个session，其他浏览器不能访问其他的session
* 存在于服务器的内存中
* 默认生命周期为30分钟
    * session的生命周期为发呆时间，即在该生命周期时间内未访问该session，就消亡，若重新访问，则重新计时。
    * 如果重启服务器或者reload web应用，session也会失效。
    * 也可以使用invalidate()方法使session中所有属性失效
* 设置同名属性的时候，值会被后来的替换

## 常用函数
* request.getSession(boolean)：创建一个session对象，如果传入true或者无参数，则没有session时会自动创建，如果传入false，没有session时不会自动创建
* setAttribute(String name,Object value)：向session中放入属性
* removeAttribute(name)：移除该属性
* setMaxInactiveInterval(int)：设置生命周期
* invalidate()：使session失效
* getAttribute(name)：取值
* getAttributeNames()：获取全部的session属性名放入一个Enumeration中
* getID()：获取该session的唯一id

## 设置session
* 创建session：
```java
    //访问session(没有session时，就会自动创建session)
    HttpSession session = request.getSession();
    //给该session放入属性
    session.setAttribute("name","mh");
    session.setAttribute("age",20);
    session.setAttribute("time",new Date());
    //移除属性
    session.removeAttribute("");
    out.println("创建session并放入属性");
    //设置生命周期(默认30分钟)，单位为秒；当设置时间内没有操作该session时，就会消亡
    //session.setMaxInactiveInterval();

    //使所有的session属性失效
    //session.invalidate();
```

## 访问session
```java
    //获取session
    HttpSession session = request.getSession();
    //获取内容
    String name = (String) session.getAttribute("name");
    int age = (int) session.getAttribute("age");
    Date d = (Date) session.getAttribute("time");
    out.println("name:"+name+"<br/>age:"+age+"<br/>time:"+d+"<br/>");
    
    //也可以获取全部的参数名放在Enumeration中，通过循环获取全部的属性值
    Enumeration e = session.getAttributeNames();
    while (e.hasMoreElements()){
        String n = (String) e.nextElement();
        out.println(n+":"+session.getAttribute(n));
    }
```

## session实现原理
    一个浏览器访问一个web资源，当没有session存在时，服务器会自动给该浏览器创建一个session，这个session有一个自己的id号，在发送HTTP响应时，会把这个id号放在cookie中发送给浏览器。
    浏览器下次再访问该web资源时，请求头中会带上上次返回的id号，当服务器接收到请求头中的id号时，会匹配与该ID号对应的session
### 问题
    当把session的id号放在cookie中保存后，由于cookie的默认生命周期为浏览器关闭后就消亡，session的默认生命周期为发呆时间30分钟，所以当关闭浏览器后，cookie消失，再次打开并访问该页面，并不会把session的id放在HTTP请求中发过去，所以就算在session的生命周期内，也无法访问该session。
    解决方案：在创建session时，手动创建一个Cookie存放session id并设置与session生命周期相同的时间后保存
```java
//Cookie名必须与自动创建的保存session id的Cookie名相同
Cookie c = new Cookie("JSESSIONID",session.getId());
c.setMaxAge(30*60);
response.addCookie(c);
```


## 生成验证码的demo
```java
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "CreateCode", urlPatterns = {"/CreateCode"})
public class CreateCode extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //禁止浏览器缓存随机图片
        response.setDateHeader("Expires",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        //通知客户端以图片的方式打开发送的数据
        response.setHeader("Content-Type","image/jpeg");

        //1 在内存中创建一幅图片
        BufferedImage image = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);

        //2 在图片上写数据
        Graphics g = image.getGraphics();
        //设置背景色
        g.setColor(Color.white);
        g.fillRect(0,0,80,30);

        //3 设置写入数据的颜色和字体
        g.setColor(Color.red);
        g.setFont(new Font(null,Font.BOLD,20));

        //向图片上写数据
        String num = makeNum();
        request.getSession().setAttribute("checkcode",num);
        g.drawString(num,0,20);

        //把写好的图片输出给浏览器
        //ImageIO是一个工具类,可以把指定的图片添加到输出流中
        ImageIO.write(image,"jpg",response.getOutputStream());

    }

    //随机产生七位数的函数
    public String makeNum(){
        Random r = new Random();
        String num = r.nextInt(9999999)+"";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 7-num.length(); i++) {
            stringBuffer.append("0");
        }
        num = stringBuffer.toString()+num;
        return num;
    }
}
```