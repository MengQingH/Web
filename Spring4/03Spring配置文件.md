## beans
配置文件的根节点，内包含多个bean标签。还可以在属性中引入schema约束。相关约束：
1. xmlns：xmlns是XML NameSpace的缩写，因为XML文件的标签名称都是自定义的，自己写的和其他人定义的标签很有可能会重复命名，而功能却不一样，所以需要加上一个namespace来区分这个xml文件和其他的xml文件，类似于Java中的package。

2. xmlns:xsi：指xml文件遵守xml规范。即schema资源文件中定义的元素遵守sml规范
3. xmlns:p：使用p标签的命名空间
4. xmlns:aop：启动AOP功能的命名空间
5. xmlns:tx：启动声明式事务的命名空间
6. xmlns:c：使用c标签的命名空间
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
在配置文件中声明一个实体类（一种创建对象的方式？），声明该类之后可以使用spring容器创建对象。
1. 属性：
    * id：为该类创建一个唯一标识

    * class：声明的实体类的全限定路径
    * factory-bean：声明一个工厂类
    * factory-method：声明工厂类中创建对象的方法
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
当需要为对象中的属性设置值时使用。该标签是调用了对象的set方法为属性赋值。
1. 标签属性：
    * name：对象中的参数名

    * ref：为对象类型的属性赋值，引用另一个对象对应的bean标签的id
    * value：为基本数据类型的属性赋值
2. 赋值子标签：除了使用标签的属性为对象中的属性赋值，还可以在property标签中使用子标签为属性赋值
    * value：为基本数据类型赋值

    * ref：引用另一个bean给属性赋值，引用另一个对象对应的bean标签的id
    * set：给对象中的Set属性赋值，使用时需要在set标签中嵌套多个value（基本数据类型）或ref（对象）标签表示set中的值
    * list：给对象中的List属性赋值，也是在list标签中使用value或ref标签表示其中的值。当list中只有一个值时，可以直接使用value属性赋值（常用）
    * array：赋值方法与list相同
    * map：给对象中的map属性赋值，在map标签中使用entry子标签表示一个键值对，entry标签中可以使用key或value属性设置值，也可以使用一个key标签和一个其他表示值的标签来设置值
    * prop：当属性为properties类型时使用该标签赋值，该标签的key属性表示键名，标签中的内容表示键对应的值
3. 示例：
```xml
<bean id="peo3" class="com.mh.pojo.People">
    <!-- 使用属性和子标签为对象中的属性赋值 -->
    <property name="id" value="11"></property>
    <property name="name">
        <value>mh</value>
    </property>

    <!-- 给Set赋值 -->
    <property name="set">
        <set>
            <value>value1</value>
            <value>value2</value>
            <value>value3</value>
        </set>
    </property>

    <!-- 给List赋值 -->
    <!-- list中只有一个值时可以使用value赋值 -->
    <property name="list" value="1"></property>
    <property name="list">
        <list>
            <value>1</value>
        </list>
    </property>

    <!-- 数组 -->
    <!-- 数组中只有一个值时可以直接使用value属性赋值 -->
    <property name="arr" value="1"></property>
    <property name="arr">
        <array>
            <value>1</value>
        </array>
    </property>

    <!-- 给map赋值 -->
    <property>
        <!-- 方法一 -->
        <map>
            <entry key="key" value="value"></entry>
        <map>
        <!-- 方法二 -->
        <map>
            <entry>
                <key>key</key>
                <value>value</value>
            </entry>
        <map>
    </property>

    <!-- 给properties类型的属性赋值 -->
    <property>
        <prop key="key">value</prop>
    </property>
</bean>
```
