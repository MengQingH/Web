## ThreadLocal
一个线程容器类，属于jdk的内容。可以给线程绑定一个Object对象，只要线程不变，随时可以取出线程中的内容。
```java
ThreadLocal<String> t = new ThreadLocal<>();
//给线程绑定内容
t.set("test");
//取出线程中的内容
String s = t.get();
System.out.println(s);//test

//新建一个子线程
new Thread(){
    public void run(){
        String s = t.get();
        System.out.println(s);//null
    }
}.start();
```

## 使用MyBatis工具类
1. 创建SqlSession对象时，需要用到工厂设计模式，构建SqlSessionFactory对象非常消耗性能，并且代码重复，所以可以使用工具类来生成SqlSession对象。
```java
//MyBatisUtil.java
public class MyBatisUtil {
    //factory的实例化是一个非常耗费性能的过程，所以要保证有且只有一个factory
    //在类中新建一个静态的factory，由于factory的实例化无法一句语句完成，所以在静态块中实例化该对象，来保证只有一个factory对象
    private static SqlSessionFactory factory;
    //使用ThreadLocal工具类来保证在servlet中获取的SqlSession对象是同一个
    private static ThreadLocal<SqlSession> tl = new ThreadLocal<>();
    static {
        try {
            InputStream is = Resources.getResourceAsStream("myBatis.xml");
            factory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession对象，如果该线程中有session对象，就获取，没有就创建一个session对象放入ThreadLocal对象中
    //在service类中获取SqlSession对象就使用该方法
    public static SqlSession getSession(){
        SqlSession session = tl.get();
        if (session==null) {
            tl.set(factory.openSession());
        }
        return tl.get();
    }
    public static void closeSession(){
        SqlSession session = tl.get();
        if (session!=null){
            session.close();
        }
        tl.set(null);
    }
}
```
2. 在过滤器中，对一个servlet进行过滤相当于在一个servlet前后执行某些代码，此时线程为一个线程。所以可以使用filter对所有的servlet进行操作，给当前线程创建一个ThreadLocal对象并附加一个session对象，并在servlet操作完成后对session进行关闭。服务器收到请求--过滤器过滤--servlet处理--调用service，整个过程为一个线程，调用MyBatisUtl.getSession方法获取的也是同一个SqlSession对象
```java
//创建OpenSessionInView过滤器
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    SqlSession session = MyBatisUtil.getSession();

    //对sql的执行过程进行try-catch处理，一旦执行过程出现问题就回滚
    try {
        chain.doFilter(req, resp);
        session.commit();
    } catch (Exception e) {
        session.rollback();
        e.printStackTrace();
    }finally {
        MyBatisUtil.closeSession();
    }
}
```