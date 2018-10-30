# 给bean对象赋值（也叫注入）
## 1. 通过构造方法设置值

## 2. 设置注入
使用property标签为对象中的属性赋值。该标签是调用了对象的set方法为属性赋值。
1. 标签属性：
    * name：对象中的参数名

    * ref：为对象类型的属性赋值
    * value：为基本数据类型的属性赋值
2. 赋值子标签：除了使用标签的属性为对象中的属性赋值，还可以在property标签中使用子标签为属性赋值
    * value：为基本数据类型赋值

    * ref：引用另一个bean给属性赋值
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
