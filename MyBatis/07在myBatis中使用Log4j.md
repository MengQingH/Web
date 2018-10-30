myBatis中有对log4j的支持，需要在配置文件中打开该日志输出方式。在使用myBatis时会使用日志的方式输出sql语句、参数和返回值等相关信息

## 步骤：
1. 导入log4j的相关jar包
2. 在src目录下新建log4j.properties配置文件
3. 在myBatis全局配置文件中通过<setting>标签开启log4j
```xml
<settings>
    <setting name="logImpl" value="LOG4J">
</settings>
``` 
4. 可以在log4j配置文件中对myBatis中的局部内容设置单独的输出级别（即myBatis中mapper映射下定义的方法）。
    1. properties文件中的设置方式
        ```java
        //包级别：该包下所有的mapper文件中定义的方法都设置为该输出级别
        log4j.logger.mapper映射文件的包名 = 输出级别
        //类级别：该标签中定义的方法都设置为
        log4j.logger.mapper文件中namespace属性的值 = 输出级别
        //方法级别
        log4j.logger.namespace属性值.标签id = 输出级别
        ```
    2. xml文件中的设置方式：
        ```xml
        <!-- 包级别 -->
        <logger name="mapper">
            <level value="error" />
        </logger>
        <!-- 类级别 -->
        <logger name="mapper.a.b">
            <level value="error" />
        </logger>
        <!-- 方法级别 -->
        <logger name="mapper.a.b.selAll">
            <level value="error" />
        </logger>
        ```