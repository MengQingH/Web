## Log4j
由apache推出的日志处理的类库。

## 作用：
在项目中编写System.out.println()输出到控制台，当项目发布到tomcat后，没有控制台（在命令行界面也能看见），不容易观察到输出结果。而使用log4j，不仅能把内容输出到控制台，还能把内容输出到文件中，便于观察结果

## 使用步骤：
1. 导入jar包
2. 在src下新建log4j.properties文件(名称和路径都不能改变)。注：log4j中的2.X中有几个版本不能使用properties文件配置，只能使用xml格式
3. 在需要输出日志信息的地点新建Logger对象，并调用相应的函数输出
    * logger.debug(message);
    * logger.info(message);
    * logger.warm(massage);
    * ···


## xml文件配置：
引入dtd：<!DOCTYPE log4j:configuration PUBLIC "-//log4j/log4j Configuration//EN" "log4j.dtd">

### log4j:configuration标签
根标签。属性：
* xmlns:log4j：取定值为http://jakarta.apache.org/log4j/

### appender标签
表示日志信息输出目的地。name属性表示该标签的唯一id；class属性表示该标签的输出目的地，类型参考properties设置中的记录
```xml
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    <!-- 表示添加一个输出地点 -->
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <!-- Param标签表示参数设置。 -->
        <!-- Target属性表示输出目的地，可以设置为System.out：标准输出设备（缓冲显示屏）、System.err：标准错误设备（不缓冲显示屏） -->
        <param name="Target" value="System.out" />
        <!-- layout标签表示输出格式控制 -->
        <layout class="org.apache.log4j.PatternLayout">
            <!-- 输出格式中的参数设置 -->
            <param name="ConversionPattern" value="%d %-5p: %c - %m%n" />
        </layout>
        <!--过滤器设置输出的级别-->
        <filter class="org.apache.log4j.varia.LevelRangeFilter">
            <!-- 设置日志输出的最小级别 -->
            <param name="levelMin" value="INFO"/>
            <!-- 设置日志输出的最大级别 -->
            <param name="levelMax" value="ERROR"/>
        </filter>
    </appender>
    
    <!-- 输出日志到文件 -->
    <appender name="fileAppender" class="org.apache.log4j.FileAppender">
        <!-- 输出文件全路径名-->
        <param name="File" value="/data/applogs/own/fileAppender.log"/>
        <!--是否在已存在的文件追加写：默认时true，若为false则每次启动都会删除并重新新建文件-->
        <param name="Append" value="false"/>
        <param name="Threshold" value="INFO"/>
        <!--是否启用缓存，默认false-->
        <param name="BufferedIO" value="false"/>
        <!--缓存大小，依赖上一个参数(bufferedIO), 默认缓存大小8K  -->
        <param name="BufferSize" value="512"/>
        <!-- 日志输出格式 -->
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="[%p][%d{yyyy-MM-dd HH:mm:ss SSS}][%c]-[%m]%n"/>
        </layout>
    </appender>
 
 
    <!-- 输出日志到文件，当文件大小达到一定阈值时，自动备份 -->
    <!-- FileAppender子类 -->
    <appender name="rollingAppender" class="org.apache.log4j.RollingFileAppender">
        <!-- 日志文件全路径名 -->
        <param name="File" value="/data/applogs/RollingFileAppender.log" />
        <!--是否在已存在的文件追加写：默认时true，若为false则每次启动都会删除并重新新建文件-->
        <param name="Append" value="true" />
        <!-- 保存备份日志的最大个数，默认值是：1  -->
        <param name="MaxBackupIndex" value="10" />
        <!-- 设置当日志文件达到此阈值的时候自动回滚，单位可以是KB，MB，GB，默认单位是KB，默认值是：10MB -->
        <param name="MaxFileSize" value="10KB" />
        <!-- 设置日志输出的样式 -->`
        <layout class="org.apache.log4j.PatternLayout">
            <!-- 日志输出格式 -->
            <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%-5p] [method:%l]%n%m%n%n" />
        </layout>
    </appender>
 
 
    <!-- 日志输出到文件，可以配置多久产生一个新的日志信息文件 -->
    <appender name="dailyRollingAppender" class="org.apache.log4j.DailyRollingFileAppender">
        <!-- 文件文件全路径名 -->
        <param name="File" value="/data/applogs/own/dailyRollingAppender.log"/>
        <param name="Append" value="true" />
        <!-- 设置日志备份频率，默认：为每天一个日志文件 -->
        <param name="DatePattern" value="'.'yyyy-MM-dd'.log'" />
 
        <!--每分钟一个备份-->
        <!--<param name="DatePattern" value="'.'yyyy-MM-dd-HH-mm'.log'" />-->
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="[%p][%d{HH:mm:ss SSS}][%c]-[%m]%n"/>
        </layout>
    </appender>
</log4j:configuration>
```
1. 输出格式：
    * org.apache.log4j.ConsoleAppender（控制台）
    * org.apache.log4j.FileAppender（文件）
    * org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
    * org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
    * org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
2. 输出格式控制：
    * org.apache.log4j.HTMLLayout（以HTML表格形式布局）
    * org.apache.log4j.PatternLayout（可以灵活地指定布局模式）
        * ConversionPattern：使用PatternLayout方式配置布局选项时，可使用该属性详细定义输出日志的输出格式，
    * org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串）
    * org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）


### logger标签
表示为某个路径或者路径下的某个类单独设置日志输出级别。name属性表示路径，level标签的value表示日志的输出等级
```xml
<log4j:configuration namespace="http://jakarta.apache.org/log4j/">
    <logger name="com.mh">
        <level value="error" />
    </logger>
</log4j:configuration>
```


## properties配置文件
```java
//配置根Logger
log4j.rootLogger=[level],appenderName,appenderName2

//配置日志信息的输出目的地Appender及其选项
log4j.appender.appenderName=class
log4j.appender.appenderName.option1=value1
···
log4j.appender.appenderName.optionN=valueN
//配置日志信息的格式及其选项
log4j.appender.appenderName.layout=calss
log4j.appender.appenderName.layout.option1=value1
···
log4j.appender.appenderName.layout.optionN=valueN
```

### 1. 配置根Logger
log4j.rootLogger=[level],apendName1,apendName2,··· 
1. level表示log输出信息的级别，可选择上面输出级别中的一个，设置某个输出级别后信息大于等于该级别的信息才能被输出。
2. 输出级别：fatal（致命错误） > error（错误） > warm（警告） > info（普通信息） >  debug（调试信息）
3. apendName：表示输出信息的地点，，可以同时指定多个。值为下面配置的输出目的地的appendName值

### 2. 配置日志信息的输出目的地Appender及Appender选项
appendName可自定义，相当于id属性。可以在rootLogger中使用该配置。
```java
log4j.appender.appenderName=class
log4j.appender.appenderName.option1=value1
log4j.appender.appenderName.layout=class
log4j.appender.appenderName.layout.option1=value1
```
1. class值：
    * org.apache.log4j.ConsoleAppender（控制台）
    * org.apache.log4j.FileAppender（文件）
    * org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
    * org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
    * org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
2. option选项：
    * File（文件相关的目的地中含有该选项）：输出文件路径
    * Append（文件）：是否向日志文件中直接添加还是先清空再添加
3. layout：配置日志信息的格式（布局）及格式布局选项
    1. class值：
        * org.apache.log4j.HTMLLayout（以HTML表格形式布局）
        * org.apache.log4j.PatternLayout（可以灵活地指定布局模式）
            * ConversionPattern：使用PatternLayout方式配置布局选项时，可使用该属性详细定义输出日志的输出格式，
        * org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串）
        * org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）
    2. option选项：参数控制

        
## 格式化日志
* %c：包名+类名
* %m：输出代码中指定的消息；
* %n：输出一个回车换行符
* %d：输出日志的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyyy-MM-dd HH:mm:ss,SSS}，输出类似：2002-10-18 22:10:28,921；
* %l：输出日志事件的发生位置，及在代码中的行数。
* %M 输出打印该条日志的方法名；
* %p 输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL；
* %r 输出自应用启动到输出该log信息耗费的毫秒数；
* %t 输出产生该日志事件的线程名；


## 示例：
```java
public class Test{
    public static void mian(String[] args){
        Logger logger = Logger.getLogger();
        logger.debug("输出调试信息");
        logger.info("输出普通信息");
        logger.error("输出错误信息");
    }
}
```