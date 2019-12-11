Mapper.xml映射文件中定义了操作数据库的sql，每个sql是一个statement。Mapper映射文件也可以引入相应的dtd规范：
```xml
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```
可能包含的元素有（在mapper标签下）：
### mapper标签中的namespace属性：
表示方法的全路径（可随意指定。但是接口实现映射文件的时候只能使用[mapper文件的包名+mapper文件的文件名]，即文件的全限定路径）


## select
映射相应的sql语句。在标签内部写sql语句。可能包含以下属性：
* id：方法名
* parameterType：输入的参数类型，可以省略
* resultType：把结果集映射为响应的java对象类型。
* statementType：STATEMENT PREPARED CALLABLE中的一个，表示使用Statement、PreparedStatement和CallableStatement。默认使用PreparedStatement
```xml
<mapper namespace="a.b">
    <!--id：映射得到方法名
    parameterType：定义参数类型
    resultType：返回值类型，如果返回值是List，则需要写明List的泛型，因为myBatis是对jdbc进行封装，一行一行读取数据
    resultMap：使用外部定义的resultMap，resultType和resultMap属性只能用一个
    -->
    <select id="selAll" resultType="pojo.Flower">
        select * FROM  flower
    </select>
</mapper>
```

### 预处理语句有参数时的情况
1. #{id}表示告诉myBatis创建一个预处理语句参数，通过jdbc，这样的一个参数在sql中会用一个占位符?来标识，即："select * from flower where id=?"。获取参数的方式：
    * 使用索引，有#{0}#{param1}两种方式，两种都表示第一个
    * 如果只有一个参数时，对内容没有要求。
    * 如果参数是对象，#{属性名}}
    * 如果对象是map，#{key}
    * 需要传入多个参数时，可以用map或者对象来实现
    ```xml
    <select id="selAll" parameterType="int" resultType="pojo.Flower">
        <!-- 使用参数时，可以使用#{0}和#{param1}表示参数的次序，#{0}和#{param1}表示第一个 -->
        <!-- 当参数只有一个的时候且为基本数据类型或String时，myBatis对{}中的内容没有要求 -->
        select * FROM  flower where id=#{}
    </select>
    ```
2. ${}表示用字符串拼接的方式构成sql语句，不使用?。
    * 如果${}中的是数字，参数就会使用该数字；
    * 如果${}中使用的是字符串内容，参数就会向调用时传入的参数中获取该属性，即使用该方式时，参数需要使用类。
    ```xml
    <select id="selAll" parameterType="pojo.Flower" resultType="pojo.Flower">
        <!-- 调用时传入参数为flower对象，myBatis会从该对象中获取id属性当作sql语句中的参数 -->
        select * FROM  flower where id=${id}
    </select>
    ```

### 查询实现分页
分页查询语句：select * from table limit (pageNow-1)*pageSize, pageSize。由于使用#{}方式时不能在sql语句中计算，所以在select标签中，可以使用语句select * from table limit #{pageStart}
```xml
<select id="selAll" parameterType="map" resultType="pojo.Flower">
    select * FROM  flower limit #{pageStart},#{pageSize}
</select>
```


## ResultMap
1. 在select查询语句中，如果resultType的类型为map，myBatis会自动把结果集中的列名和列值放入到map中返回。
    ```xml
    <select id="selAll" resultType="map">
        select * FROM  flower
    </select>
    ```
2. 如果使用了resultType的值为相应的Javabean或者是pojo对象，myBatis会基于属性名把ResultSet中的内容映射到Javabean对象的属性中，再返回该Javabean对象。（AutoMappinng特性）
3. 如果Javabean中的属性名和数据库中的列名不相同，可以在select语句中对列名使用别名来匹配，在列名后加上实体类中的对应的属性名。如：
    ```java
        "select id flower_id,name flower_name,price,production from flower"
    ```
### 或者使用外部ResultMap：
在resultMap标签中可以定义Javabean类和数据库中的列名的映射。使用resultMap时，select标签不使用resultType属性，而是使用resultMap属性传入一个resultMap标签的id
1. 属性
    * id：对该resultMap进行标识
    * type：类名
    * autoMapping：为该ResultMap开启或关闭自动映射
2. 子标签：
    * id和result：把Javabean中的属性名和数据库中的列名对应。主键使用id标签，其他列使用result标签。属性：
        * property：Javabean中的属性名
        * column：数据库中对应的列名
    * association：关联一个其他的实体类的对象
        * property：对象在类中的属性名
        * select：使用一个其他映射中的sql语句返回一个Java实体类型
        * column：把当前列表的哪列的值作为参数传递给select中的查询
    * collection：关联多个其他实体类对象（通常以一个集合的形式）
        * javaType：集合中的对象类型
        * property：
        * select：
        * column：
```xml
<resultMap id="map1" type="Flower">
    <!-- 主键使用id标签 -->
    <id property="flower_id" column="id">
    <!-- 其他键使用result标签 -->
    <result property="flower_name" column="name">
</resultMap>

<!-- 在select语句中使用resultMap -->
<select id="selAll" resultMap="map1">
    select * FROM  flower
</select>
```


## insert update delete
映射相应的sql语句。myBatis中使用executeUpdate()执行新增修改删除，指定该函数返回一个int，表示受影响的行数。属性：
* id：标识该元素
* parameterType：参数的类型
* useGeneratedKeys：true表示有自动生成的主键
* kayProperty：自动生成的主键名
* statementType：Statement的类型
```xml
<mapper>
    <!-- 映射插入语句 -->
    <insert id="insertUser">
    insert into User (id,username,password,email,bio)
    values (#{id},#{username},#{password},#{email},#{bio})
    </insert>
    <!-- 有主键时的插入 -->
    <insert id="insertUser" parameterType="User" useGeneratedKeys="true" keyProperty="id">
    insert into User (username,password,email,bio) values (#{username},#{password},#{email},#{bio})
    </insert><!-- 或者 -->
    <insert id="insertUser" parameterType="User">
    insert into User (id,username,password,email,bio) values (default,#{username},#{password},#{email},#{bio})
    </insert>
    
    <!-- 映射更新语句 -->
    <update id="updateUser" parameterType="User">
    update User set
        username = #{username},
        password = #{password},
        email = #{email},
        bio = #{bio}
    where id = #{id}
    </update>

    <!-- 映射删除语句 -->
    <delete id="deleteAuthor" parameterType="int">
    delete from User where id = #{id}
    </delete>
</mapper>
```