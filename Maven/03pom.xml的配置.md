# 项目之间关系的标签
详情在02
1. parent：表示该项目的父项目的相关信息
2. module：表示该项目的具有聚合关系的子项目
3. dependencies：该标签下使用dependency表示要导入的包和项目等

# 其他标签
1. properties：类似.properties文件，可以在该标签中配置变量和信息，在其他地方使用${name}获取这些信息
```xml
<properties>
    <!-- 标签名任意，为变量名；标签中的值为变量的值 -->
    <name>value</name>
    <version>4.1.6</version>
</properties>
```
2. dependencyManagement：全局包版本管理。pom.xml文件中，包版本的声明有两种方式：
    1. dependency中可以使用version标签声明引入的包的版本
    2. 如果没有使用version声明版本，那么maven就会到dependencyManagement查看有没有对该包声明版本，如果有，使用该标签声明的版本。
    * 如果两种方式都有，dependency下声明的version优先
    * dependencyManagement标签下声明的包不会导入。只是声明使用该包的版本。

```xml
<!-- 使用时常和properties标签一起使用 -->
<properties>
    <Spring-core>4.1.6</Spring-core>
</properties>
<!-- 不会导入包 -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${Spring-core}</version>
        </dependency>
    </dependencies>
<dependencyManagement>

<!-- 会导入包 -->
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.1.2.RELEASE</version>
    </dependency>
</dependencies>
```



# 添加依赖
1. 配置当前项目依赖的其他项目：
```xml
<dependencies>
    <!-- 配置需要导入项目的相关信息 -->
    <dependency>
        <groupId>com.mh</groupId>
        <artifactId>java</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
2. 导入jar包：导入一个jar包后如果仓库中没有该包会自动从中央仓库中下载该包和该包依赖的其他包
    1. 在https://mvnrepository.com下查找需要导入的jar包
    2. 找到相应的版本，会有该包maven的配置方式（该包的dependency），把该dependency标签复制到dependencies标签下即可完成导入
    * scope标签可以表示jar包的有效范围，取值为provided表示编译期生效，不会打包发布到tomcat中
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.1.2.RELEASE</version>
    </dependency>
</dependencies>
```

# Maven插件
1. tomcat插件：在一个web项目中使用该插件会把该web项目发布到插件中指定的服务器中。分布式项目可以使用该方式实现把不同的模块运行到不同的服务器中
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.2</version>
            <configuration>
                <!--控制tomcat的端口号-->
                <port>80</port>
                <!--发布到tomcat后的项目名-->
                <path>/</path>
            </configuration>
        </plugin>
    </plugins>
</build>
```
2. 资源拷贝插件：maven默认只把src/mian/resources目录下的非Java文件编译到classes中，如果希望java下的文件也被编译到classes中，可以使用该插件
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>*.xml</include>
            </includes>
        <resource>
    <resources>
</build>
```