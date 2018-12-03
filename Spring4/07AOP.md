# AOP
Aspect Oriented Programming，面向切面编程。正常的程序执行流程为按代码顺序纵向先后执行，而AOP就是在一个方法的前面或者后面添加一些功能，称为前置通知和后置通知，相当于向原有的代码中添加了一个切面。
```
          method1
             ↓
前置通知  + method2 + 后置通知 
             ↓
          method3
```
1. 不需要修改原有代码
    * 具有高扩展性
    * 原有功能释放了部分逻辑
2. 常用概念
    * 原有功能：切点（pointcut）

    * 前置通知：在切点之前执行的功能（before advice）
    * 后置通知：在切点之后执行的功能（after advice）
    * 异常通知：如果在切点执行过程中出现异常，就会触发该通知（throw advice）
    * 切面：所有功能的总称叫切面
    * 织入：把切面嵌入到原有功能的过程叫织入
3. 使用aop功能需要导入依赖的两个jar包：aopalliance和aspectjweaver


# 配置AOP
配置AOP前需要在beans属性中添加aop的约束：
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       AOP的约束↓
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
        并且需要添加上面两个url ↑↑↑↑
```
## aop:config标签
在此标签中配置AOP的相关信息，AOP的配置信息都放在该标签中

## aop:pointcut标签
声明一个切点，并以该切点形成一个切面。属性：
1. id：标识该切点的id
2. expression：匹配一个或多个方法形成切点。该属性是一个execution()固定表达式，
    1. 表达式的形式为：execution(修饰符 返回值类型 方法所在类的路径名.类名.方法名(参数类型) 异常类型?) and args()
        * and args()表示给匹配的方法参数起一个名字，给下面配置通知类时使用。
    2. 其中可以使用标识符：
        * 通配符 * ：可以匹配任意方法名，任意类名，任意一级包名，任意修饰符，任意返回值类型
        * .. ：匹配任意的方法参数
    3. 示例：
        * 匹配任意公共方法：execution(public *.*(..))
        * 匹配任意set方法：execution(* set*(..))
        * 匹配包下的任意方法：execution(* com.mh.service.*.*(..))

```xml
<aop:config>
    <aop:pointcut id="mypoint" expression="execution(* test.method2(..))"/>
</aop:config>
```

## aop:advisor
向某一个切点上添加通知。属性：
1. advice-ref：再配置文件中声明要添加的通知类，该属性为类的id
2. pointcut-ref：要添加到的切点的id，即aop:pointcut的id属性
```xml
<bean id="before" class="MyBeforeAdvice"></bean>
<aop:config>
    <aop:advisor advice-ref="before" pointcut-ref="mypoint"/>
</aop:config>
```

## aop:aspect
使用AspectJ方式实现aop时使用的标签，使用该方式需要在ref属性中声明通知类。子标签：
1. aop:pointcut：声明切点，属性：id、class
2. aop:after-throwing：声明异常通知，属性method、pointcut-ref
