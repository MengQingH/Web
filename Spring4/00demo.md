1. web.xml文件中的配置
```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

2. applicationContext.xml文件中的配置
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=GMT%2B8&amp;useSSL=false"></property>
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="username" value="root"></property>
        <property name="password" value="123456"></property>
    </bean>
    <bean id="factory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="mapper"></property>
        <property name="sqlSessionFactoryBeanName" value="factory"></property>
    </bean>
    <!-- 声明service实现类并把mapper中对应的接口赋给接口中的对象 -->
</beans>
```