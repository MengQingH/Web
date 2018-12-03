mybatis-spring.jar：spring整合myBatis的包。该包中把myBatis中相关的配置文件的属性都封装到了类中，只需要把类在spring的配置文件中声明并传入相关的参数就可以完成myBatis的配置

## 步骤：
1. 导入相关jar包：spring-jdbc, spring-tx, spring-aop, mybatis-spring

2. 编写代码：
    1. 正常创建实体对象

    2. 创建mapper文件，必须使用接口绑定的方式（可以使用xml文件或者注解，但是必须含有接口）
    3. 编写service接口和serviceImpl实现类，在实现类中创建一个mapper接口对象，并生成get和set方法
    4. spring无法管理Servlet。但是可以通过配置web项目的web.xml文件实现web应用初始化时自动加载spring配置文件。
    ```xml
    <!--上下文参数-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <!--声明监听器-->
    <listener>
        <!--spring中封装的一个类，该类可以通过上下文中的contextConfigLocation参数来获取spring配置文件的位置实现自动加载spring的配置文件-->
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    ```
    ```java
    //当使用自动加载配置文件时，spring产生的容器是一个webApplicationContext中，这个接口封装了spring和web的相关信息，是ApplicationContext的子接口。获取方法：
    //该方法需要传入一个ServletContext对象
    WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    ```

3. 在applicationContext.xml文件中配置相关信息（需要声明spring中封装了myBatis配置文件的类）
    1. 数据源--myBatis全局配置文件中的dataSource标签。数据源：获取数据库连接。

        * 需要注入数据库连接的四个参数url, driverClassName, username, password
        ```xml
        <!--数据源封装类，并传入连接数据库需要的某些属性。该类在spring-jdbc-jar中-->
        <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=GMT%2B8&amp;useSSL=false"></property>
            <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
            <property name="username" value="root"></property>
            <property name="password" value="123456"></property>
        </bean>
        ```
    2. 声明封装了SqlSessionFacroty的类。

        * 需要设置对象的dataSource属性，为数据库连接的信息，需要传入一个数据源对象的id
        ```xml
        <!--封装了SqlSessionFactory的类org.mybatis.spring.SqlSessionFactoryBean-->
        <bean id="factory" class="org.mybatis.spring.SqlSessionFactoryBean">
            <!--设置dataSource的值-->
            <property name="dataSource" ref="dataSource"></property>
            <!-- 给包中的类起别名 -->
            <property name="typeAliasesPackage" value="com.mh.pojo"></property>
        </bean>
        ```
    3. 扫描器--myBatis全局配置文件中的mappers标签的package标签，用来声明mapper包。扫描后传入的包后会给包中的接口创建对象，创建的对象名称为接口名首字母小写
        * 需要传入basePackage属性，即package的路径

        * 需要关联SqlSessionFactory，传入sqlSessionFactory对象的id
        ```xml
        <!--org.mybatis.spring.mapper.MapperScannerConfigurer封装了扫描器的类-->
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <!--basePackage属性表示要扫描的包，value表示mapper包的路径-->
            <property name="basePackage" value="mapper"></property>
            <!--关联SqlSessionFactory，传入sqlSessionFactory对象的id-->
            <property name="sqlSessionFactoryBeanName" value="factory"></property>
        </bean>
        ```
    4. 声明servece。由于class属性中传入的类是用来实例化的，所以只能传入实现类，不能传入接口。

        * 需要给serviceImpl中定义的mapper对象赋值，为扫描器中生成的mapper文件夹中的对象，id为接口名首字母小写
        ```xml
        <!--声明service类-->
        <bean id="airportService" class="com.mh.serviceImpl.AirportServiceImpl">
            <!-- 给对象中的mapper对象赋值 -->
            <property name="airportMapper" ref="airportMapper"></property>
        </bean>
        ```

## 示例
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