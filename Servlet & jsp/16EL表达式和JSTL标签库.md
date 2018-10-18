# 域对象总结
1. request对象
    * 生命周期为一次请求
    * 只有该请求内可以使用
2. session对象
    * 生命周期为发呆时间，默认为30分钟，可设置
    * 一个浏览器对应一个session对象
3. ServletContext对象
    * 生命周期为服务器启动时间
    * 所有servlet和jsp共享其中的信息


# EL表达式
Expression Language,一种写法非常方便的表达式，语法简单，便于使用，可简化在jsp中获取作用域对象中的数据或者请求数据的写法。
### 语法结构：${expression},提供了.和[]两种方式来存取数据

## 使用EL表达式获取作用域数据
* 获取作用域数据：${expression}EL表达式会在四个域对象中查找相应的对象，若找到则返回该对象，找不到则返回""
    * ${键名}：获取键名对应的对象，相当于.getAttribute(String)方
    * ${键名[n]}//获取数组或列表中的某个值，相当于.getAttribute(String)[n]
    * ${键名.属性名}//获取对象中的某个属性值，或获取HashMap中的某个key对应的对象   
* 作用域查找顺序
pageContext-->request-->session-->application(从小到大进行查找，找到了则获取，找不到继续查找)

## 使用EL表达式进行运算
${运算表达式}，EL表达式支持以下运算符
* 四则运算：+-*/,此处+只是运算符，不是字符串连接符
* 关系运算符：==或eq(等于),!=或ne(不等于),<或lt(小于),gt或>(大于),<=或le(小于等于),=>或ge(大于等于)
* 逻辑运算符：&&或and(且),||或or(或),!或not(非)
* empty运算符：${empty 键名}：判断该键名对应的对象是否为空，若空返回true，不空返回false
* 二元运算符：${boolean:value1?value2}

## 获得web开发常用对象
EL表达式中定义了11个隐含对象，使用这些隐含对象可以很方便的获取web开发中的一些常用对象。语法：${隐式对象名称}，使用${对象名.键名}可以返回这些对象中保存的对象
* pageContext：对应jsp页面中的pageContext对象(注意是对象)
* pageScope：pageContext域对象中用于保存属性的Map对象
* requestScope：request域对象中用于保存属性的Map对象
* sessionScope：session域对象中用于保存属性的Map对象
* applicationScope：application域对象中用于保存属性的Map对象
* param：保存了所有请求参数的Map对象
* paramVales：保存了返回值为String[]的请求的Map对象
* header：保存了所有http请求头的Map对象，若请求头名称含有-，则用""包起来，如${header["Accept-Encoding"]}
* headerValues：保存了所有http请求头的Map对象，该对象返回一个String[]
* cookie：表示一个保存了所有Cookie的Map对象
* initParam：表示一个保存了所有web应用初始化参数的map对象


# JSTL标签库
JSTL是对EL表达式的扩展，是一种标签语言，为了方便在jsp中编写逻辑相关的语句。不是jsp内置的标签，需要导包
```js
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

## JSTL核心标签库
1. <c:out value=""></c:out>：输出语句，可输出value属性的内容，其中可传入字符串或者EL表达式。其他属性：
    * default：如果value的值为空时所输出的默认值
    * escapeXml：Boolean，是否将><&"等字符串进行HTML编码后进行输出，默认为true
2. <c:set></c:set>：把一个对象存储到指定的域对象中，或者把值存储到Map或者JavaBean中。存储方式：
    * <c:set var="键名" value="值" scope="域对象名"></c:set>：
    * <c:set var="键名" scope="域对象">值</c:set>：把该键值对储存在指定的域对象{page,request,session,application}中
    * <c:set value="值" target="JavaBean对象" property="属性名"></c:set>：
    * <c:set target="JavaBean对象" property="属性名">值</c:set>：把值存储到指定Javabean对象的指定属性中。
3. <c:remove var="变量名" scope=""></c:remove>：移除指定的变量，也可以指定域对象
4. <c:catch var="">产生异常的代码</c:catch>：用于捕获产生的异常对象，保存在page这个web域中
5. <c:if test"boolean" var="" scope="">标签体内容</c:if>：如果test为true，则执行标签体内容。var和scope属性可添加或者不添加表示存放的test属性的执行结果并放入指定的域对象中。
6. 流程控制<c:choose><c:when><c:otherwise>嵌套使用可组成类似if-else的结构：
```
<c:choose>
    <c:when test="">
        //业务
    </c:when>
    <c:when test="">
        //业务
    </c:when>
    <c:otherwise>
        //业务
    </c:otherwise>
</c:choose>
```
7. <c:forEach>遍历内容</c:forEach>根据条件遍历集合中的内容
```
    <c:forEach
        var=""设定变量名用于从集合中取出元素
        item=""要遍历的集合
        varStatus=""设定变量名，用于存放集合中元素的信息：index当前循环的索引值，count循环的次数，first last是否为第一个、最后一个位置，boolean
        begin=""起始位置(可选)
        end=""终止位置(可选)
        step=""指定循环的步长
    >遍历内容</c:forEach>
```