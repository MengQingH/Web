# 实现步骤
spring提供了两种AOP的实现方式：Schema-base方式和AspectJ方式。

## Schema-based：
* 每一个通知都需要实现接口或类，区分不同通知类型是靠类实现不同的通知接口或类来区分
* 在spring配置文件中的aop:config标签中配置
### Schema-based实现步骤
1. 创建通知类（需要实现相应的接口并重写相应的方法）
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


## AspectJ：
* 不需要实现接口，区分不同的通知是靠不同的接口类型来区分
* 使用该方法配置通知时，需要指明实现通知的方法。所以一些没有重写函数的通知只能使用该方法配置
* 在配置文件中aop:config标签的子标签aop:aspect标签中配置
### 使用该方式获取参数的方法：
使用AspectJ方式获取参数的方法：
1. 获取切点时通过execution给方法的参数

### 实现步骤：
1. 新建异常通知类，并在类中定义一个或多个不同的通知方法
    ```java
    public class MyAdvice {
        public void myBefore(){
            System.out.println("前置通知");
        }
        public void myAfter(){
            System.out.println("后置通知1");
        }
        public void myAfterReturning(){
            System.out.println("返回通知2");
        }
        public void myThrowing(){
            System.out.println("异常通知");
        }
        // 需要传入ProceedingJoinPoint类型的一个参数，该参数的proceed方法可以切换到后置
        public Object myArround(ProceedingJoinPoint p) throws Throwable {
            System.out.println("环绕通知-前置");
            Object proceed = p.proceed();
            System.out.println("环绕通知-后置");
            return proceed;
        }
    }
    ```
2. 在配置文件中声明通知类
    ```xml
    <bean id="myAdvice" class="MyAdvice"></bean>
    ```
3. 配置通知。由于使用AspectJ方式配置通知时，没有实现接口，所以需要在配置时声明通知类、通知的类型和通知方法：
    * 在aop:aspect标签中使用ref属性声明通知类的id
    * 通知标签中声明方法和切点的id
    * 通知标签的类型：
        * aop:pointcut：声明切点
        * aop:before：声明前置通知
        * aop:after：声明后置通知（当切点方法执行结束后执行，不论出不出现错误都执行通知）
        * aop:after-returning：声明返回通知（当切点方法执行完毕返回后执行，出现错误不执行通知）
        * aop:after-throwing：声明异常通知
            * aop:around：声明环绕通知
    ```xml
    <aop:config>
            <!-- 声明通知类是哪一个 -->
            <aop:aspect ref="myAdvice">
                <aop:pointcut id="point" expression="execution(* test.method2(..))"/>
                <aop:before method="myBefore" pointcut-ref="point"/>
                <aop:after method="myAfter" pointcut-ref="point"/>
                <aop:after-returning method="myAfterReturning" pointcut-ref="point"/>
                <aop:after-throwing method="myThrowing" pointcut-ref="point"/>
                <aop:around method="myArround" pointcut-ref="point"/>
            </aop:aspect>
        </aop:config>
    ```

