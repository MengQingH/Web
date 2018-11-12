## 使用注解配置Spring
1. spring不会自动寻找注解，如果使用注解必须指明spring哪些包下的类中可能有注解。添加xmlns:context命名空间（用于指明有注解的包）：
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       使用注解需要的约束↓↓↓↓
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
        还需要添加上面的两个↑↑↑↑
```
2. 使用context:component-scan标签指明要使用注解的包，如果有多个包中间用,隔开
```xml
<context:component-scan base-package="com.mh.advice,com.mh.pojo"></context:component-scan>
```

## 注解类型：
1. Component：配置对象。默认id为类名首字母小写，也可以传入自定义id。以下注解功能相同，但是用在不同的场景
```java
@Component("类的id")
@Service("")//写在ServiceImpl类中
@Repository("")//写在数据访问层类上
@Controller("")//写在控制器上
```
2. Resource和Autowired：自动注入。
    * Resource是Java中的注解；Autowired是Spring中的注解
    * 不需要属性的get set方法
    * Resource默认使用byName方式注入，如果没有名称相同的属性名，则使用byType注入；Autowired默认使用byType注入
3. Value：使用注解快速的把配置文件中的值附给类中的属性
```java
@Value("${key}") // 要取值的properties文件必须在配置文件中声明
```


<!按如下配置通知不执行>
## 使用注解配置AOP
基于AspectJ方式，相比配置文件配置只是把标签换成了注释
1. 在配置文件中设置为静态代理设计模式
```xml
<!--如果属性设置为true，使用cglib动态代理；false为jdk动态代理-->
<aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
```
2. 配置实体类。
```java
@Component()
```
3. 配置切点方法：使用@Pointcut()注解，传入一个execution表达式
```java
@Component()
public class TestAnnot {
    @Pointcut("execution(* com.mh.pojo.TestAnnot.method())")
    private void method(){
        System.out.println("切点");
    }
}
```
4. 配置通知类@Aspect和通知方法：
	* @Before：前置通知
	* @After：后置通知 
	* @AfterRunning：返回通知
	* @AfterThrowing：异常通知
	* @Around：环绕通知
```java
// 实现方法1：通过配置一个切点，然后在通知注释中传入切点方法的路径来实现
// 实现方法2：直接在通知中传入一个execution表达式，该表达式适配的方法都会执行该通知
@Component
@Aspect
public class Advice {
    @Before("com.mh.pojo.TestAnnot.method()")
    public void myBefore(){
        System.out.println("前置");
    }

    @After("com.mh.pojo.TestAnnot.method()")
    public void myAfter(){
        System.out.println("后置");
    }
    
    @AfterReturning("com.mh.pojo.TestAnnot.method()")
    public void myAfterReturning(){
        System.out.println("返回通知2");
    }
    
    @AfterThrowing("com.mh.pojo.TestAnnot.method()")
    public void myThrowing(){
        System.out.println("异常通知");
    }
    
    @Around("com.mh.pojo.TestAnnot.method()")
    public Object myArround(ProceedingJoinPoint p) throws Throwable {
        System.out.println("环绕通知-前置");
        Object proceed = p.proceed();
        System.out.println("环绕通知-后置");
        return proceed;
    }
}
```