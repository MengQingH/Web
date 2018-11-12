## beans
配置文件的根节点，内包含多个bean标签。还可以在属性中引入schema约束。相关约束：
1. xmlns：xmlns是XML NameSpace的缩写，因为XML文件的标签名称都是自定义的，自己写的和其他人定义的标签很有可能会重复命名，而功能却不一样，所以需要加上一个namespace来区分这个xml文件和其他的xml文件，类似于Java中的package。

2. xmlns:xsi：指xml文件遵守xml规范。即schema资源文件中定义的元素遵守sml规范
3. xmlns:p：使用p标签的命名空间
4. xmlns:aop：启动AOP功能的命名空间
```xml
<!-- 使用到的约束和约束的url -->
<beans
    xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    上面两个是基本的命名空间，不可少
    xmlns:aop="http://www.springframework.org/schema/aop"
>
```

## bean
在配置文件中声明一个对象，声明的是一个对象，Spring会在application中创建该对象。当创建多个该对象的实例时，实际上只是一个对象的多个引用。
1. 属性：
    * id：为该对象创建一个唯一标识

    * class：声明的实体类的全限定路径
    * factory-bean：声明一个工厂类
    * factory-method：声明工厂类中创建对象的方法
    * autowire：实现自动注入
    * scope：设置对象实例化的方式（单例多例或者是其他）
2. 示例：
```xml
<bean id="peo" class="com.mh.pojo.People"></bean>
<bean id="peo1" factory-bean="factory" factory-method="newInstance"></bean>
```
### constructor-arg标签：
当需要使用有参构造方式创建对象时，需要在bean标签中使用该标签声明参数的值。属性：
1. 获取参数，至少使用一个，当有类中有多个有参构造方法时，也可以使用多个精确的找出想要使用的构造方法
    * index：参数的索引，从0开始

    * name：参数的参数名
    * type：参数的类型
2. 给参数赋值
    * ref：当参数为一个对象时使用，引用另一个对象对应的bean标签的id

    * value：当参数是基本数据类型时使用

示例：
```xml
<bean id="peo" class="com.mh.pojo.People">
    <constructor-arg index="0" value="1"></constructor-arg>
    <constructor-arg index="1" value="mh"></constructor-arg>
</bean>
```
### property标签：
当需要为对象中的属性设置值时使用。该标签是调用了对象的set方法为属性赋值。<a href="05给对象赋值.md">详细使用方法</a>
1. 标签属性：
    * name：对象中的参数名

    * ref：为对象类型的属性赋值，引用另一个对象对应的bean标签的id
    * value：为基本数据类型的属性赋值
2. 赋值子标签：除了使用标签的属性为对象中的属性赋值，还可以在property标签中使用子标签为属性赋值
    * value：为基本数据类型赋值

    * ref：引用另一个bean给属性赋值，引用另一个对象对应的bean标签的id
    * set：给对象中的Set属性赋值
    * list：给对象中的List属性赋值
    * array：赋值方法与list相同
    * map：给对象中的map属性赋值
    * prop：当属性为properties类型时使用该标签赋值
3. 示例：
```xml
<bean id="peo3" class="com.mh.pojo.People">
    <!-- 使用属性和子标签为对象中的属性赋值 -->
    <property name="id" value="11"></property>
    <property name="name">
        <value>mh</value>
    </property>
```

## bean对象的scope属性：
设置对象的实例化方式，单例、多例或者是其他的方式。属性值：
* singleton：创建对象时只实例化一次
* prototype：创建对象时，获取一次对象就实例化一次
* request：每次请求重新实例化
* session：每个会话内对象是单例的
* application：在application内是单例的