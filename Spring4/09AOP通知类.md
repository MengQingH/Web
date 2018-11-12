
# 使用Schema-base方式实现时需要实现的接口和重写的方法

## 前置通知：
实现MethodBeforeAdvice接口并重写before方法，其中before方法的属性：
* Method method：利用反射原理获取的切点方法，获取方法名：method。getName()
* Object[] objects：切点方法参数
* Object o：该方法所属的对象
```java
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("方法："+method+";方法名："+method.getName());
        System.out.println("参数列表"+objects);
        System.out.println("方法的对象"+o);
        System.out.println("执行前置通知");
    }
}
```
### 实现方式（Schema-base方式）
1. 创建通知类（需要实现相应的接口并重写方法）
```java
//实现了MethodBeforeAdvice接口并重写before方法相当于创建了一个前置通知
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("执行前置通知");
    }
}
```
2. 在配置文件中声明该通知类
```xml
<bean id="before" class="MyBeforeAdvice"></bean>
```
3. 配置切面：把一个方法声明为一个切点，并向切点上添加通知类
```xml
<aop:config>
    <!-- 声明一个切点，id为切点的标识，expression为方法的信息 -->
    <aop:pointcut id="mypoint" expression="execution(* test.method2(..))"/>
    <!-- 向切点中添加一个通知，advice-ref为该通知类的id，pointcut-ref为切点的id -->
    <aop:advisor advice-ref="before" pointcut-ref="mypoint"/>
</aop:config>
```

## 后置通知
实现AfterReturningAdvice接口并重写afterReturning方法，属性：
* Object o：切点方法的返回值
* Method method：切点方法
* Object[] objects：参数列表
* Object o1：方法所属的对象
```java
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("执行后置切点");
    }
}

```
### 实现方法
与前置通知相同


## 返回通知
只能通过AspectJ方式实现，当切点方法执行完毕后执行该通知。当方法中出现错误的时候不执行该通知。



## 异常通知
只有切点报异常才能触发异常通知。实现步骤：
### AspectJ方式：
1. 新建异常通知类并实现ThrowsAdvice接口，在接口中定义一个通知方法声明出现异常时执行的操作
```java
public class MyThrowAdvice implements ThrowsAdvice {
    public void exception() {
        System.out.println("异常通知");
    }
}
```
2. 在配置文件中声明该类
```xml
<bean id="advice" class="MyThrowAdvice"></bean>
```
3. 使用AspectJ方式配置异常通知。由于使用该方式配置通知时，没有重写方法，所以需要在配置时声明异常通知类和异常方法是哪个。
    * 在aop:aspect中声明通知类
    * 在aop:after-throwing中声明method通知方法和pointcut-ref切点
    * 如果需要获取异常时还需要使用throwing属性声明参数的名称
```xml
<aop:config>
    <!-- ref属性声明异常通知类是哪个 -->
    <aop:aspect ref="advice">
        <!-- 声明切点 -->
        <aop:pointcut id="myPoint" expression="execution(* test.method2(..))"/> 
        <!-- 声明异常通知，需要指明方法名和切点 -->
        <!-- 如果需要获取异常时还需要使用throwing属性声明方法中的参数名 -->
        <aop:after-throwing method="exception" pointcut-ref="myPoint" throwing="name"></aop:after-throwing>
    </aop:aspect>
</aop:config>
```
### Schema-base方式：
1. 创建通知类实现ThrowsAdvice接口并创建下面的两个方法中的一个。该方法是Spring中定义的方法，实现了该方法就可以使用Schema-base方式来配置通知。
```java
public void afterThrowing(Exception x) throws Throwable{
    System.out.println("通过Schema-base方式");
}
public void afterThrowing(Method m, Object[] args, Object target, Exception x){
    System.out.println("通过Schema-base方式");
}
```
2. ···


## 环绕通知
把前置通知和后置通知都写到一个类中，组成了环绕通知。
### Schema-base方式：
1. 创建环绕通知类并实现Advice、MethodInterceptor接口，重写invoke方法。调用mi.proceed()方法可以从前置通知转为后置通知，方法的返回值为invoke方法的返回值。
```java
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyArround implements MethodInterceptor, Advice {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        System.out.println("环绕前置");
        Object o = mi.proceed();
        System.out.println("环绕后置");
        return o;
    }
}
```
2. ···