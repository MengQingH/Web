# 把shiro整合到ssm中
1. 添加shiro的相关jar包到项目中
    ```xml
    <properties>
        <shiro.version>1.3.2</shiro.version>
    </properties>
    <!--shiro-->
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-core</artifactId>
        <version>${shiro.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-web</artifactId>
        <version>${shiro.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>${shiro.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-ehcache</artifactId>
        <version>${shiro.version}</version>
    </dependency>
    ```
2. 在web.xml中添加shiro的配置
    ```xml
    <!--配置shiro的filter：通过代理来配置，对象由Spring创建，交给servlet容器来管理-->
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <!--表示bean的生命周期由servlet管理-->
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <!--表示在Spring容器中bean的id，如果不配置该属性，那么默认和该filter的name一致-->
            <param-name>targetBeanName</param-name>
            <param-value>shiroFilter</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```
3. 配置Spring的配置文件
    ```xml
    <!--shiro-->
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <!--Filter需要配置一个securityManager-->
        <property name="securityManager" ref="securityManager"/>
        <!--当访问需要认证的资源时，如果没有认证，将跳转到该url。如果不配置该属性，默认情况下会到根路径下的login.jsp-->
        <property name="loginUrl" value="/LoginUrl"/>
        <!--配置认证成功后跳转到那个url上，通常不设置。如果不设置，那么默认认证成功后跳转到上一个url
        <property name="successUrl"/>-->
        <!--配置用户没有权限访问资源时跳转的页面-->
        <property name="unauthorizedUrl" value="/refuse"/>
        <!--配置shiro的过滤器链-->
        <property name="filterChainDefinitions">
            <!--配置访问一个页面需要的权限；设置静态资源不拦截
                anon：表示可以匿名访问的页面
                authc表示必须认证之后才能访问的页面-->
            <value>
                /toLoginUrl=anon
                /login=authc
                /logout=logout
                /**=authc
                /js/**=anon
                /css/**=anon
            </value>
        </property>
    </bean>
    <!--创建一个securityManager-->
    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <!--securityManager中需要配置realm-->
        <property name="realm" ref="userRealm"/>
    </bean>
    <!--创建一个Realm-->
    <bean id="userRealm" class="com.mh.realm.UserRealm">
        <property name="credentialsMatcher" ref="credentialsMatcher">
    </bean>
    <!-- 如果需要加密，则配置下面的凭证匹配器 -->
    <bean id="credentialsMatcher" class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
        <!-- 设置加密算法类型 -->
        <property name="hashAlgorithmName" value="md5"/>
        <!-- 设置迭代次数 -->
        <property name="hashIterations" value="2"/>
    </bean>
    ```
4. 添加自定义Realm。在自定义Realm中通过MyBatis框架获取数据库中的密码、盐值等用户信息，并把信息返回。