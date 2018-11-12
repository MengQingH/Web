## 使用Spring框架
1. 导入相关的jar包
2. 在src目录下创建applicationContext.xml配置文件
    * 名称和路径随意
    * spring配置文件基于schema。<a href="D:\VSCodeDoc\其他\xml文件.md">schema相关</a>
3. 把创建的实体类标识在配置文件中
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 在配置文件中标识一个实体类 -->
    <!--id标识获取对象时使用的标识，class标识实体类的全限定路径-->
    <bean id="peo" class="com.mh.pojo.People"></bean>
</beans>
```
4. 使用框架创建新的实体类对象
```java
//加载配置文件，配置文件被加载时，容器就创建对象
//AppliactionContext接口是spring中最基本的一个容器。实现类ClassPathXmlApplicationContext会从classes根目录下来世查找文件
ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//使用容器创建一个实体类，getBean(bean标签的id, 返回值类型)，如果没有返回值类型，默认返回Object
People people = ac.getBean("peo", People.class);
//使用该方法可以获取Spring中管理的所有实体类
String[] names = ac.getBeanDefinitionNames();
System.out.println(people);
```

## 在idea中使用Spring框架
1. 导入相关的jar包
2. 新建applicationContext配置文件
3. 进入Project Structure，点击Modules选择项目，点击+号为项目添加Spring模块
4. 再点击右边的+号，绑定刚刚创建的applicationContext.xml文件
