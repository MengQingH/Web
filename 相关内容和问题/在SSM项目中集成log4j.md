1. 在pom.xml文件中导入log4j相关的包
```xml
<!-- log4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.7</version>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.7</version>
</dependency>
```

2. 在resource或java目录下新建log4j.property文件
```properties
log4j.rootLogger=DEBUG, console, log

###Console ###
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern = %d %p[%C:%L]- %m%n

### log ###
log4j.appender.log = org.apache.log4j.DailyRollingFileAppender
log4j.appender.log.File = ${catalina.base}/logs/debug.log
log4j.appender.log.Append = true
log4j.appender.log.Threshold = DEBUG
log4j.appender.log.DatePattern='.'yyyy-MM-dd
log4j.appender.log.layout = org.apache.log4j.PatternLayout
log4j.appender.log.layout.ConversionPattern = %d %p[%c:%L] - %m%n
```

3. 在web.xml文件中打开log4j过滤器
```xml
<!-- log4j -->
<listener>
    <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
</listener>
```

## 在log4j中输出指定内容的日志
控制某个局部内容的日志级别。在配置文件中添加：
```
log4j.logger.包名或类名或方法名 = 级别
```
表示该包（类或方法）下的日志为该级别，其他的日志为设定的级别